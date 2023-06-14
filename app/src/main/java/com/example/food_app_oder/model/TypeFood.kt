import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TypeFood(

    @SerializedName("id_type_food") @Expose var id: Int?,
    @SerializedName("type_name") @Expose var name: String?,
    @SerializedName("image") @Expose var imagePath: String?,
)