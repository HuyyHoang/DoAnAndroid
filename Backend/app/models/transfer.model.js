module.exports = (sequelize, Sequelize) => {
    const Transfer = sequelize.define("transfers", {
      amount: {
        type: Sequelize.INTEGER
      },
      description: {
        type: Sequelize.STRING
      },
      from_account_id: {
        type: Sequelize.INTEGER
      },
      to_account_id: {
        type: Sequelize.INTEGER
      },
      daymonth: {
        type: Sequelize.STRING
      },
      hour:{
        type: Sequelize.STRING
      }
    });
  
    return Transfer;
  };