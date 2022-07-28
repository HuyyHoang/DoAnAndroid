module.exports = (sequelize, Sequelize) => {
    const Account = sequelize.define("accounts", {
      name: {
        type: Sequelize.STRING
      },
      amount: {
        type: Sequelize.INTEGER,
      }
    });
  
    return Account;
  };