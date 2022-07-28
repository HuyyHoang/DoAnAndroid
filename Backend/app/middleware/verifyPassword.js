const db = require("../models");
const User = db.user;

var bcrypt = require("bcryptjs");

checkPassword  = (req, res, next) => {
    User.findOne({
    where: {
      username: req.body.username
    }
    }).then(user => {

        var IsOldPassword = bcrypt.compareSync(
            req.body.oldPassword,
            user.password
        );
        
        if (!IsOldPassword) {
            res.status(210).send({
                message: "Old password is not correct!"
            });
            return;
        }

        IsDuplicate = bcrypt.compareSync(
            req.body.newPassword,
            user.password
        );

        if (IsDuplicate) {
            res.status(220).send({
                message: "The new password is the same as the old password!"
            });
            return;
        }

        res.status(200).send({
            message: "Success!"
        });

        next();
    })
}

const verifyPassword = {
    checkPassword: checkPassword
  };
  
module.exports = verifyPassword;