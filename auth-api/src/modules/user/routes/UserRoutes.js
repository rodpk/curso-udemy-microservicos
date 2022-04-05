import { Router } from 'express';
import UserController from '../controller/UserController.js';
import express from "express";

const router = new Router();
//const bp = require('body-parser');

router.use(express.json());


router.get('/api/user/email/:email', UserController.findByEmail);
router.post('/api/user/auth', UserController.getAccessToken);

export default router;