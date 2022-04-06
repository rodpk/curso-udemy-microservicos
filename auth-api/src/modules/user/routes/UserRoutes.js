import { Router } from 'express';
import UserController from '../controller/UserController.js';
import express from "express";
import checkToken from "../../../config/auth/checkToken.js"

const router = new Router();
//const bp = require('body-parser');

router.use(express.json());

router.post('/api/user/auth', UserController.getAccessToken);

// o que ta em cima nao usa token de autenticacao
router.use(checkToken);
router.get('/api/user/email/:email', UserController.findByEmail);

export default router;