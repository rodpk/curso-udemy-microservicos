import bcrypt from 'bcrypt';

import User from '../../modules/user/model/User.js';

export async function createInitialData() {


    try {
        await User.sync({ force: true }); // força a tabela a sincronizar os dados
        await User.create({
            name: 'rodpk',
            email: 'rodp.rodrigopinheiro@gmail.com',
            password: await bcrypt.hash('admin', 10)
        });
    } catch(err) {
        console.log(err);
    }
}