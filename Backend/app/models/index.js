const config = require("../config/db.config.js");

const Sequelize = require("sequelize");
const sequelize = new Sequelize(
  config.DB,
  config.USER,
  config.PASSWORD,
  {
    host: config.HOST,
    dialect: config.dialect,
    operatorsAliases: false,

    pool: {
      max: config.pool.max,
      min: config.pool.min,
      acquire: config.pool.acquire,
      idle: config.pool.idle
    }
  }

);

const db = {};

db.Sequelize = Sequelize;
db.sequelize = sequelize;

db.user = require("../models/user.model.js")(sequelize, Sequelize);
db.otp  = require("../models/otp.model.js")(sequelize,Sequelize);
db.budget = require("../models/budget.model.js")(sequelize,Sequelize);
db.category = require("../models/category.model.js")(sequelize,Sequelize);
db.transaction = require("../models/transaction.model.js")(sequelize,Sequelize);
db.transfer = require("../models/transfer.model.js")(sequelize,Sequelize);
db.account = require("../models/account.model.js")(sequelize,Sequelize);
db.bank = require("../models/bank.model.js")(sequelize,Sequelize);

db.user.hasMany(db.account);
db.account.belongsTo(db.user);

db.user.hasMany(db.budget);
db.category.hasMany(db.budget);
db.budget.belongsTo(db.user);
db.budget.belongsTo(db.category);

db.account.hasMany(db.transaction);
db.category.hasMany(db.budget);
db.transaction.belongsTo(db.account);
db.transaction.belongsTo(db.category);

db.account.hasMany(db.transfer);
db.transfer.belongsTo(db.account);

db.user.hasMany(db.otp);
db.otp.belongsTo(db.user);

db.bank.hasMany(db.account);
db.account.belongsTo(db.bank);

module.exports = db;
