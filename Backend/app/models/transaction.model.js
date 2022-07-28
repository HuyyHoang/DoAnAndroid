module.exports = (sequelize, Sequelize) => {
    const Transaction = sequelize.define("transactions", {
      name: {
        type: Sequelize.STRING
      },
      amount: {
        type: Sequelize.INTEGER
      },
      description: {
        type: Sequelize.STRING
      },
      location: {
        type: Sequelize.STRING
      },
      image: {
        type: Sequelize.STRING
      },
      daymonth: {
        type: Sequelize.STRING
      },
      hour:{
        type: Sequelize.STRING
      }
    });
  
    return Transaction;
  };