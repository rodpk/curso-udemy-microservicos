import Sequelize from 'sequelize';

const sequelize = new Sequelize("auth-db", "admin", "123456", {
    host: "localhost",
    dialect: "postgres",
    quoteIdentifiers: false, // nao quero que ele mexe na nomenclatura
    define: {
        syncOnAssociation: true,
        timestamps: false,
        underscored: true,
        underscoredAll: true,
        freezeTableName: true // vai adicionar no plural caso contrario
    },
});

sequelize.authenticate().then(() => {
    console.info("Conectado com sucesso");
}).catch((err) => {
    console.error("Erro ao conectar: ", err.message);
});

export default sequelize