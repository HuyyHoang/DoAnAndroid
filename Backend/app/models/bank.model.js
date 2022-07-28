module.exports = (sequelize, Sequelize) => {
    const Bank = sequelize.define("banks", {
      name: {
        type: Sequelize.STRING
      }
    }, {
        timestamps: false
    });
    return Bank;
};
  