package xyz.leezoom.cloudmemo

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import com.danielstone.materialaboutlibrary.ConvenienceBuilder
import com.danielstone.materialaboutlibrary.MaterialAboutActivity
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem
import com.danielstone.materialaboutlibrary.items.MaterialAboutTitleItem
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard
import com.danielstone.materialaboutlibrary.model.MaterialAboutList
import com.mikepenz.community_material_typeface_library.CommunityMaterial
import com.mikepenz.iconics.IconicsDrawable
import org.jetbrains.anko.share
import org.jetbrains.anko.toast

class AboutActivity : MaterialAboutActivity() {

  override fun getActivityTitle(): CharSequence? = getString(R.string.about_title)

  override fun getMaterialAboutList(p0: Context): MaterialAboutList {
    val appCardBuilder = MaterialAboutCard.Builder()
    appCardBuilder.addItem(MaterialAboutTitleItem.Builder()
            .text(R.string.app_name)
            .desc("© 2018 ReindeerLee")
            .icon(R.mipmap.ic_launcher)
            .setOnClickAction {
              val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "分享 ${getString(R.string.app_name)}： 分享自@酷安网  https://www.coolapk.com/apk/xyz.leezoom.cloudmemo")
                type = "text/plain"
              }
              startActivity(Intent.createChooser(intent, getString(R.string.about_shar)))
            }
            .build())

    appCardBuilder.addItem(ConvenienceBuilder.createVersionActionItem(this,
            IconicsDrawable(this)
                    .icon(CommunityMaterial.Icon.cmd_information_outline)
                    .color(Color.BLACK)
                    .sizeDp(18),
            getString(R.string.about_version),
            false))

    appCardBuilder.addItem(MaterialAboutActionItem.Builder()
            .text(getString(R.string.about_license))
            .subText("GPL-3.0")
            .icon(IconicsDrawable(this)
                    .icon(CommunityMaterial.Icon.cmd_book)
                    .color(Color.BLACK)
                    .sizeDp(18))
            .setOnClickAction(ConvenienceBuilder.createWebsiteOnClickAction(this, Uri.parse("https://www.gnu.org/licenses/gpl-3.0")))
            .build())

    appCardBuilder.addItem(MaterialAboutActionItem.Builder()
            .text(getString(R.string.about_hwo_to))
            .subTextHtml("""<blockquote>
<p>欢迎使用平行世界的笔记，在这里与其他世界的人类分享你的碎碎念吧。</p>
</blockquote>
<strong>说明书：</strong>
<ol>
<p>* 你拥有一个其他人无法进入的隐藏世界（世界名是你的用户名）</p>
<p>* 点击菜单可以添加一个平行的世界</p>
<p>* 点击 Toolbar 切换世界</p>
<p>* 笔记状态：</p>
<p>* 小锁符号 表示这条笔记是否可以编辑。</p>
<p>* 眼睛符号 表示这个世界的人类是否可以看见这条笔记。</p>
<p>* 注意：只有笔记的创建者可以修改笔记的状态。</p>
<p>* Σ(｀L_｀ )</p>
</ol>
""")
            .showIcon(false)
            .build())

    val authorCardBuilder = MaterialAboutCard.Builder()
    authorCardBuilder.title(getString(R.string.about_author))

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
            .text(getString(R.string.about_github))
            .icon(IconicsDrawable(this)
                    .icon(CommunityMaterial.Icon.cmd_github_circle)
                    .color(Color.BLACK)
                    .sizeDp(18))
            .setOnClickAction(ConvenienceBuilder.createWebsiteOnClickAction(this, Uri.parse("https://github.com/LeeReindeer/Parallel-Memo")))
            .build())

    val otherCardBuilder = MaterialAboutCard.Builder()
    otherCardBuilder.title(getString(R.string.about_contact))

    otherCardBuilder.addItem(MaterialAboutActionItem.Builder()
            .text(getString(R.string.about_telegram))
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
            getString(R.string.about_rate),
            null))

    val licenseCardBuild = MaterialAboutCard.Builder()
    licenseCardBuild.title(getString(R.string.about_license))

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
