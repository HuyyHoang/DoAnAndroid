module.exports = (sequelize, Sequelize) => {
    const Otp = sequelize.define("otps", {
      otp: {
        type: Sequelize.STRING
      },
      expireTime: {
        type: Sequelize.DATE
      }
    });
  
    return Otp;
  };