const authJwt = require("./authJwt");
const verifySignUp = require("./verifySignUp");
const verifyPassword = require("./verifyPassword");
const verifyOtp = require("./verifyOtp");

module.exports = {
  authJwt,
  verifySignUp,
  verifyPassword,
  verifyOtp
};
