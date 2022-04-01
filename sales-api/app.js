import express from 'express';

const app = express();
const env = process.env;
const PORT = env.PORT || 8082;

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