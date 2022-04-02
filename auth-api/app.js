import express from "express";


import * as db from "./src/config/db/initialData.js";

const app = express();
const env = process.env;
const PORT = env.PORT || 8081; // porta padrão ou 8081 caso não seja informada


db.createInitialData();

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