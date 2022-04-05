import User from '../model/User.js';

class UserRepository {


    async findById(id) {
        try {
            return await User.findOne({ // como é o mesmo nome de atributo pode manter
                where:  { email }
            });
        } catch(err) {
            console.log(err.message);
            return null;
        }
    }

    async findByEmail(email) {
        try {
            return await User.findOne({ // como é o mesmo nome de atributo pode manter
                where:  { email }
            });
        } catch(err) {
            console.log(err.message);
            return null;
        }
    }
}





export default new UserRepository();