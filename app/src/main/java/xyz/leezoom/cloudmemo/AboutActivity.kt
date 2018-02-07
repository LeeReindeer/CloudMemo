package xyz.leezoom.cloudmemo

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.util.Log
import com.danielstone.materialaboutlibrary.ConvenienceBuilder
import com.danielstone.materialaboutlibrary.MaterialAboutActivity
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem
import com.danielstone.materialaboutlibrary.items.MaterialAboutTitleItem
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard
import com.danielstone.materialaboutlibrary.model.MaterialAboutList
import com.mikepenz.community_material_typeface_library.CommunityMaterial
import com.mikepenz.iconics.IconicsDrawable
import org.jetbrains.anko.toast

class AboutActivity : MaterialAboutActivity() {

  override fun getActivityTitle(): CharSequence? = "About"

  override fun getMaterialAboutList(p0: Context): MaterialAboutList {
    val appCardBuilder = MaterialAboutCard.Builder()
    appCardBuilder.addItem(MaterialAboutTitleItem.Builder()
            .text(R.string.app_name)
            .desc("© 2018 Reindeer Lee")
            .icon(R.mipmap.ic_launcher)
            .build())

    appCardBuilder.addItem(ConvenienceBuilder.createVersionActionItem(this,
            IconicsDrawable(this)
                    .icon(CommunityMaterial.Icon.cmd_information_outline)
                    .color(Color.BLACK)
                    .sizeDp(18),
            "Version",
            true)
            .setOnClickAction { toast("check update") })

    appCardBuilder.addItem(MaterialAboutActionItem.Builder()
            .text("License")
            .subText("GPL-3.0")
            .icon(IconicsDrawable(this)
                    .icon(CommunityMaterial.Icon.cmd_book)
                    .color(Color.BLACK)
                    .sizeDp(18))
            .setOnClickAction(ConvenienceBuilder.createWebsiteOnClickAction(this, Uri.parse("https://www.gnu.org/licenses/gpl-3.0")))
            .build())

    val authorCardBuilder = MaterialAboutCard.Builder()
    authorCardBuilder.title("Author")

    authorCardBuilder.addItem(MaterialAboutActionItem.Builder()
            .text("Reindeer Lee")
            .subText("Hey:D")
            .icon(IconicsDrawable(this)
                    .icon(CommunityMaterial.Icon.cmd_account)
                    .color(Color.BLACK)
                    .sizeDp(18))
            .setOnClickAction(ConvenienceBuilder.createWebsiteOnClickAction(this, Uri.parse("https://github.com/LeeReindeer")))
            .build())


    authorCardBuilder.addItem(MaterialAboutActionItem.Builder()
            .text("View on GitHub")
            .icon(IconicsDrawable(this)
                    .icon(CommunityMaterial.Icon.cmd_github_circle)
                    .color(Color.BLACK)
                    .sizeDp(18))
            .setOnClickAction(ConvenienceBuilder.createWebsiteOnClickAction(this, Uri.parse("https://github.com/LeeReindeer/Parallel-Memo")))
            .build())

    val otherCardBuilder = MaterialAboutCard.Builder()
    otherCardBuilder.title("Convenience")

    otherCardBuilder.addItem(MaterialAboutActionItem.Builder()
            .text("Find me on Telegram")
            .icon(IconicsDrawable(this)
                    .icon(CommunityMaterial.Icon.cmd_telegram)
                    .color(Color.BLACK)
                    .sizeDp(18))
            .setOnClickAction(ConvenienceBuilder.createWebsiteOnClickAction(this, Uri.parse("https://t.me/LeeReindeer")))
            .build())

    otherCardBuilder.addItem(ConvenienceBuilder.createRateActionItem(this,
            IconicsDrawable(this)
                    .icon(CommunityMaterial.Icon.cmd_star)
                    .color(Color.BLACK)
                    .sizeDp(18),
            "Rate this app",
            null))

    val licenseCardBuild = MaterialAboutCard.Builder()
    licenseCardBuild.title("License")

    initLicenseCard(licenseCardBuild)

    return MaterialAboutList(appCardBuilder.build(), authorCardBuilder.build(), otherCardBuilder.build(), licenseCardBuild.build())

  }

  private fun initLicenseCard(licenseCardBuild: MaterialAboutCard.Builder) {
    licenseCardBuild.addItem(MaterialAboutActionItem.Builder()
            .text("Android Support Library")
            .showIcon(false)
            .subText("https://developer.android.com/topic/libraries/support-library/")
            .setOnClickAction(ConvenienceBuilder.createWebsiteOnClickAction(this, Uri.parse("https://developer.android.com/topic/libraries/support-library/index.html")))
            .build())
    licenseCardBuild.addItem(MaterialAboutActionItem.Builder()
            .text("Kotlin")
            .showIcon(false)
            .subText("https://github.com/JetBrains/kotlin")
            .setOnClickAction(ConvenienceBuilder.createWebsiteOnClickAction(this, Uri.parse("https://github.com/JetBrains/kotlin")))
            .build())
    licenseCardBuild.addItem(MaterialAboutActionItem.Builder()
            .text("Anko")
            .showIcon(false)
            .subText("https://github.com/Kotlin/anko")
            .setOnClickAction(ConvenienceBuilder.createWebsiteOnClickAction(this, Uri.parse("https://github.com/Kotlin/anko")))
            .build())
    licenseCardBuild.addItem(MaterialAboutActionItem.Builder()
            .text("Android KTX")
            .showIcon(false)
            .subText("https://github.com/android/android-ktx")
            .setOnClickAction(ConvenienceBuilder.createWebsiteOnClickAction(this, Uri.parse("https://github.com/android/android-ktx")))
            .build())
    licenseCardBuild.addItem(MaterialAboutActionItem.Builder()
            .text("Android-Iconics")
            .showIcon(false)
            .subText("https://github.com/mikepenz/Android-Iconics")
            .setOnClickAction(ConvenienceBuilder.createWebsiteOnClickAction(this, Uri.parse("https://github.com/mikepenz/Android-Iconics")))
            .build())
    licenseCardBuild.addItem(MaterialAboutActionItem.Builder()
            .text("Material about library")
            .showIcon(false)
            .subText("https://github.com/daniel-stoneuk/material-about-library")
            .setOnClickAction(ConvenienceBuilder.createWebsiteOnClickAction(this, Uri.parse("https://github.com/daniel-stoneuk/material-about-library")))
            .build())
  }
}
