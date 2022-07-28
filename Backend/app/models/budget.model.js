module.exports = (sequelize, Sequelize) => {
    const Budget = sequelize.define("budgets", {
      name: {
        type: Sequelize.STRING
      },
      total: {
        type: Sequelize.INTEGER
      },
      remain: {
        type: Sequelize.INTEGER
      },
      spent: {
        type: Sequelize.INTEGER
      },
      period: {
        type: Sequelize.DATE
      }
    });
    return Budget;
  };