const { verifySignUp } = require("../middleware");
const controller = require("../controllers/auth.controller");
const { verifyPassword } = require("../middleware");
const { verifyOtp } = require("../middleware");

module.exports = function(app) {
  app.use(function(req, res, next) {
    res.header(
      "Access-Control-Allow-Headers",
      "x-access-token, Origin, Content-Type, Accept"
    );
    next();
  });

  app.post(
    "/api/auth/signup",
    [verifySignUp.checkDuplicateUsernameOrEmail],
    controller.signup
  );

  app.post(
    "/api/auth/signin", 
    controller.signin
  );

  app.post(
    "/api/auth/checkpassword",
    [verifyPassword.checkPassword]
  );

  app.post(
    "/api/auth/change", 
    controller.changePass
  );

  app.post(
    "/api/auth/create/otp",
    controller.createOtp
  );

  app.post(
    "/api/auth/checkotp",
    [verifyOtp.checkOtp]
  );
};
