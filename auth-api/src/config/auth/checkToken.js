//middleware - sempre que o usuário fizer uma requisição, ele vai passar pelo middleware

import jwt from 'jsonwebtoken';
import { promisify } from 'util';

import * as secrets from '../constants/secrets.js';
import * as httpStatus from '../constants/httpStatus.js';
import AuthException from './AuthException.js';

const bearer = 'Bearer ';
const emptySpace = ' ';
export default async (req, res, next) => {

    try {
        const { authorization } = req.headers;
        if (!authorization) {
            throw new AuthException(httpStatus.UNAUTHORIZED, 'Token not provided.');
        }

        let accessToken = authorization;
        if (accessToken.includes(emptySpace)) {
            accessToken = accessToken.split(emptySpace)[1];
        }

        const decoded = await promisify(jwt.verify) (accessToken, secrets.API_SECRET); // separa o corpo da chamada
        req.authUser = decoded.authUser;
        return next();
    } catch (err) {
        console.log(err.message);
        const status = err.status ? err.status : httpStatus.INTERNAL_SERVER_ERROR;
        return res.status(status).json({
            status: status,
            message: err.message,
        });
    }
}
