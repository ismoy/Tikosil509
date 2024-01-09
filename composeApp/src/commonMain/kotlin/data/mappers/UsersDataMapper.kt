package data.mappers

import data.models.UsersDataModel
import domain.entities.Users

object UsersDataMapper {
    fun mapToDomain(model: UsersDataModel): Users {
        return Users(
            id = model.id,
            countryselected = model.countryselected,
            countrycode = model.countrycode,
            firstname = model.firstname,
            lastname = model.lastname,
            email = model.email,
            phone = model.phone,
            role = model.role,
            password = model.password,
            confirmpassword = model.confirmpassword,
            soldmoncash = model.soldmoncash,
            soltopup = model.soltopup,
            soldnatcash = model.soldnatcash,
            soldlapoula = model.soldlapoula,
            status = model.status,
            image = model.image
        )
    }
}