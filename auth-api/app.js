import express from "express";

import userRoutes from './src/modules/user/routes/UserRoutes.js';

import * as db from "./src/config/db/initialData.js";

import checkToken from "./src/config/auth/checkToken.js";

const app = express();
const env = process.env;
const PORT = env.PORT || 8081; // porta padrão ou 8081 caso não seja informada


db.createInitialData();

app.use(userRoutes);
app.use(express.json());

// o que ta antes n checa o token
app.use(checkToken);

app.get('/api/status', (req, res) => {
    res.status(200).json({
        service: 'Auth API',
        status: 'ok',
        message: 'API is running',
        httpStatus: 200
    });
});

app.listen(PORT, () => {
    console.info(`Servidor rodando na porta ${PORT}`);
});