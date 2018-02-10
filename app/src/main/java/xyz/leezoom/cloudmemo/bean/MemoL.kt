package xyz.leezoom.cloudmemo.bean

import com.avos.avoscloud.AVUser

import java.util.Date

//save memo in local
data class MemoL(var title: String?, var description: String?, var words: Int, var user: AVUser?, var createAt: Date)
