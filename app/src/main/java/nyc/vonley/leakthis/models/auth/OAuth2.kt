package nyc.vonley.leakthis.models.auth

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize


/**
 *    "token_type": "Bearer",
"expires_in": 31536000,
"access_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIyIiwianRpIjoiOWU0M2NjZjhlMGQ0ZmUzNTc3ZmJjNmJkNjFmNzM3ZDc5ZTg3ODQ4Mzc3ZWI1MzBmMjAzNGY3ZDg4NTNkNDc5ODEwZmU5MTNkM2NhNWUxNTUiLCJpYXQiOjE1OTk4NTczMjgsIm5iZiI6MTU5OTg1NzMyOCwiZXhwIjoxNjMxMzkzMzI4LCJzdWIiOiIxIiwic2NvcGVzIjpbIioiXX0.I3iOU5gFvIBTdZXT4yz61VsPEOpRX8Yt5jIUKHmahSun7WCLc0L98CtXrhKG75sznr9G7jIHjZdKHwuW8CpGQb0jc2UP9CCVF8GCon3Yz_Wzx6RRmHN674Knd307WyuaoTb-PDIEvgKdE8DxSzVjmWcn5SJ4961nTrg62LzJojqJ7Lkc85pnh-YpJ-3aKnK_Aw5rJyKMknWbcYIsM98koEsUS7gJwnv69hnJQvDrT_g_Gwr8pjmIZz7poZGRN1lIIt6z65hcPtiVAtuT8cN8E8moaiNZceIA7qMoscnVoXJ8smvdcvgkvvjgboH8OKDwv-5lRaiGVl7xThDtZltKuLctdGauogXHglXZHuccROwmz5CkUabN_QS8JHWukOlDGHRm3wAa9Q27i9m9dOPn4uCZUlJ-2lRyCKAFpJ3bl-8QdbmQzbWL6911ULwNDbiZtafS5yvrtFujBjaqLnhaYGCLAhjyNpwfSuwREcvRs2NRjrg2admnhJdTfqcMrTjfoliI717aA6CgohLsWHkeCuhn6R92TEUcLNyIiID80v0870Mp2nPee6G9mmxKKb1n6NweB5UrKBjv2KXekwY7n1Q_aDpWARYEfSch065nmmF4lgnmfqHYrg8KuaNUSC-dD0jq9OZtCW3n2NLD2BummROt4ACtig5D109-fqNFydU",
"refresh_token": "def502005613bc06764cd4c9f746358d61305fbe943f4bf1c16e08f34d9eb8bb234f84ae719d9991f7a9786f7893d06bea1ef08d4ce62a5a7c74800e2d8ec1e7184aec7228ad67b9a6cf3196367f352180231242497413b895b461985ae0594c499e9031e74f5f05edf497a60b7b66530ef819494535ccf034216cd619e3ff91ec035f3ad30d7082de40db11b6148fe242fff698f5db17fcc50e3341be73144256e93e243618f10507441137a68f1952816cd12e945cde5259193244e2fde89d06bcce3383116e58dfd14823ec854c594bb6347b7588e21503bc9d08cf77bb333c3849c1c5288850f3ff1408b7cad5be5182f17ec9685529b9304749a6261eb4f7732b8c83ece8cb262f954c26a0f7975dbdc89bcc00028511545ed0db06cea076878b16e02de5a54cbd4a25b3ab0b2ea2f49a461ccb40bcfdb6dec8465ba83dda7767f458b2bb286fe03e9d5c98f60121f047d6fbfea7a4db280c2f66f0b37451e02bcb"
 */

@Entity
@Parcelize
data class OAuth2(
    val token_type: String,
    val expires_in: Int,
    val access_token: String,
    val refresh_token: String
) : Parcelable {

}