module.exports = {
  HOST: "localhost",
  USER: "postgres",
  PASSWORD: "", // Type your database password
  DB: "", // Type your database name
  dialect: "postgres",
  pool: {
    max: 5,
    min: 0,
    acquire: 30000,
    idle: 10000
  }
};