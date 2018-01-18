package ir.saherelm.testdbflow.database.model;

import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import java.util.UUID;

import ir.saherelm.testdbflow.database.XAppDb;

/**
 * Contact Model
 *
 * Created by SaherElm on 13/01/2018.
 */
@Table(database = XAppDb.class)
public class ContactModel extends XBaseModel {

    @PrimaryKey
    public UUID id;

    @Column(name = "first_name")
    public String firstName;

    @Column(name = "last_name")
    public String lastName;

    @Column(name = "phone_number")
    public String phoneNumber;



    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null
                || !(obj instanceof ContactModel)) {
            return false;
        }

        ContactModel mItem = ((ContactModel) obj);
        return id == mItem.id
                && firstName.equals(mItem.firstName)
                && lastName.equals(mItem.lastName)
                && phoneNumber.equals(mItem.phoneNumber);
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    @Nullable
    public static ContactModel fromJson(String jsonStr) {
        try {
            return new Gson().fromJson(jsonStr, ContactModel.class);
        } catch (Exception ex) {
            return null;
        }
    }
}
