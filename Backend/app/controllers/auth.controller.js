const db = require("../models");
const config = require("../config/auth.config");
const User = db.user;
const Otp = db.otp;
const otpGenerator = require('otp-generator')
const Account = db.account;

var nodemailer = require('nodemailer');
const hbs = require('nodemailer-express-handlebars')
const path = require('path')

var jwt = require("jsonwebtoken");
var bcrypt = require("bcryptjs");

var transporter = nodemailer.createTransport({
  host: 'smtp.gmail.com',
  port: 587,
  secure: false,
  requireTLS: true,
  // <-------------------------------- type your mail and pass of email -------------------------------->
  auth: {
    user: '@gmail.com',
    pass: ''
  }
});

const handlebarOptions = {
  viewEngine: {
    partialsDir: path.resolve('./views/'),
    defaultLayout: false,
  },
  viewPath: path.resolve('./views/'),
};

transporter.use('compile', hbs(handlebarOptions))


exports.signup = (req, res) => {
  // Save User to Database
  User.create({
    username: req.body.username,
    email: req.body.email,
    password: bcrypt.hashSync(req.body.password, 8)
  })
  .then(user => {
    return res.status(200).send({
      message: "Register Successfully"})
  })
  .catch(err => {
    res.status(500).send({ message: err.message });
  });
};

exports.signin = (req, res) => {
  User.findOne({
    where: {
      username: req.body.username
    }
  })
    .then(user => {
      if (!user) {
        return res.status(220).send({ message: "Your username or password is not correct" });
      }

      var passwordIsValid = bcrypt.compareSync(
        req.body.password,
        user.password
      );

      if (!passwordIsValid) {
        return res.status(230).send({
          accessToken: null,
          message: "Your username or password is not correct"
        });
      }

      var token = jwt.sign({ id: user.id }, config.secret, {
        expiresIn: 3600
      });

      Account.findOne({
        where: {
          userId: user.id
        }
      })
      .then(account => {
        if(!account) {                  // if no account -> create
          return res.status(250).send({
            message: "No account",
            username: user.username,
            email: user.email,
            accessToken: token
          })    
        }
  
        res.status(200).send({
          username: user.username,
          email: user.email,
          accessToken: token
        });
      })

      
    })
    .catch(err => {
      res.status(500).send({ message: err.message });
    });
};

exports.changePass = (req, res) => {
  User.update({ password: bcrypt.hashSync(req.body.newPassword, 8) }, {
    where: {
      username: req.body.username
    }
  });
  res.status(200).send({
    message: "Password changed!"})
};

exports.createOtp = (req, res) => {
  var otpgen = otpGenerator.generate(6, {upperCase: false, alphabets : false, specialChars: false});

  var expireAt = new Date();
  User.findOne({
    where: {
      username: req.body.username
    }
  })
  .then(user => {
    if (!user) {
      return res.status(210).send({
        message: "No user is found"
      });
    }


    Otp.create({
      userId: user.id,
      otp: bcrypt.hashSync(otpgen, 8),
      expireTime: expireAt.setSeconds(expireAt.getSeconds() + 60)
    })
      .then(otp => {
        var mailOptions = {
          from: '"Lai Thiên Hải" <thienhailai1304@gmail.com>',
          to: user.email,
          subject: 'Money Management: Security Code',
          template: 'email',
          context: {
            name: req.body.username,
            otp: otpgen 
          }
        };
        transporter.sendMail(mailOptions, function(error, info){
          if (error) {
            console.log(error);
          } else {
            console.log('Email sent: ' + info.response);
          }
        });
        res.status(200).send({
          message: "Email sent"
        });
      })
      .catch(err => {
          res.status(500).send({ message: err.message });
      });
  })
  


};
