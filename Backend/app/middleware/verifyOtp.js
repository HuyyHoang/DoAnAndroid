const db = require("../models");
const Otp = db.otp;
const User = db.user;
var bcrypt = require("bcryptjs");

checkOtp = (req, res) => {
    User.findOne({
        where: {
            username: req.body.username
        }
    })
    .then(user => {
        Otp.findOne({
            where: {
                userId: user.id
            },
            order: [[ 'createdAt', 'DESC' ]]
        })
        .then(otp => {
            
            if (!otp) {
                return res.status(220).send({
                    message: "No OTP found"
                })
            }

            var now = new Date();
            if (otp.expireTime < now) {
                return res.status(230).send({
                    message: "OTP Expired!"
                });
            }
    
            var IsCorrectOtp = bcrypt.compareSync(
                req.body.otp,
                otp.otp
            );
    
            if (!IsCorrectOtp) {
                return res.status(240).send({
                    message: "OTP is not correct!"
                });
            } 
    
            res.status(200).send({
                message: "Email sent."
            })
        });
    })
}

const verifyOtp = {
    checkOtp: checkOtp
};
  
module.exports = verifyOtp;