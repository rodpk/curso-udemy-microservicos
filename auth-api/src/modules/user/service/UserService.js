import UserRepository from "../repository/UserRepository.js";
import * as httpStatus from "../../../config/constants/httpStatus.js";
import UserException from "../exception/UserException.js";
import * as secrets from "../../../config/constants/secrets.js";
import bcrypt from "bcrypt";
import jwt from "jsonwebtoken";

class UserService {
  async findById(id) {
    try {
      return await UserRepository.findById(id);
    } catch (err) {
      console.log(err.message);
      return null;
    }
  }

  async getAccessToken(req) {
    try {
      console.log("BODY::::" + req.body);
      const { email, password } = req.body;
      this.validateAccessTokenData(email, password);
      let user = await UserRepository.findByEmail(email);
      this.validateUserNotFound(user);
      await this.validatePassword(password, user.password);
      let authUser = { id: user.id, name: user.name, email: user.email };
      const accessToken = jwt.sign({ authUser }, secrets.API_SECRET, {
        expiresIn: "1d",
      });

      return {
        status: httpStatus.SUCCESS,
        accessToken: accessToken,
      };
    } catch (err) {
      console.log(err);
      return {
        status: err.status ? err.status : httpStatus.INTERNAL_SERVER_ERROR,
        message: err.message,
      };
    }
  }

  async validatePassword(pass, hashPass) {
    if (!(await bcrypt.compare(pass, hashPass))) {
      throw new UserException(
        httpStatus.UNAUTHORIZED,
        "user password is invalid."
      );
    }
  }

  validateAuthenticatedUser(user, authUser) {
    if (!authUser || user.id !== authUser.id) {
      throw new UserException(httpStatus.FORBIDDEN, "you cannot access this user data.");
    }
  }

  validateAccessTokenData(email, password) {
    if (!email || !password) {
      throw new UserException(
        httpStatus.UNAUTHORIZED,
        "user email or password was not informed."
      );
    }
  }

  async findByEmail(req) {
    try {
      const { email } = req.params;
      const { authUser } = req;
      this.validateRequestData(email);
      let user = await UserRepository.findByEmail(email);
      this.validateUserNotFound(user);
      this.validateAuthenticatedUser(user, authUser);
      return {
        status: httpStatus.SUCCESS,
        user: {
          id: user.id,
          name: user.name,
          email: user.email,
        },
      };
    } catch (err) {
      return {
        status: err.status ? err.status : httpStatus.INTERNAL_SERVER_ERROR,
        message: err.message,
      };
    }
  }

  validateRequestData(email) {
    if (!email) {
      throw new UserException(
        httpStatus.BAD_REQUEST,
        "user email was not informed."
      );
    }
  }

  validateUserNotFound(user) {
    if (!user) {
      throw new UserException(httpStatus.UNAUTHORIZED, "user not found.");
    }
  }
}

export default new UserService();
