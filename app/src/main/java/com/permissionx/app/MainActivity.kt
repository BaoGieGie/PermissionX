package com.permissionx.app

import android.Manifest
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.permissionx.jiabaodex.PermissionX

/**
 * 现在MainActivity中的逻辑是非常简洁清晰的。完全不用再去编写那些复杂的运行时权限相关的代码，只需要调用PermissionX的request()方法，
 * 传入当前的Activity和要申请的权限名，然后在Lambda表达式中处理权限的申请结果就可以了。
 * 如果allGranted等于true，就说明所有申请的权限都被用户授权了，那么就执行拨打电话操作，否则使用Toast弹出一条失败提示。
 */
class MainActivity : AppCompatActivity() {
    lateinit var makeCallBtn:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        makeCallBtn = findViewById(R.id.makeCallBtn)
        makeCallBtn.setOnClickListener {
            PermissionX.request(this,
                Manifest.permission.CALL_PHONE) { allGranted, deniedList ->
                if (allGranted) {
                    call()
                } else {
                    Toast.makeText(this, "You denied $deniedList", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun call() {
        try {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:10086")
            startActivity(intent)
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }
}

/**
 * 另外，PermissionX也支持一次性申请多个权限，只需要将所有要申请的权限名都传入request()方法中就可以了，示例写法如下：
PermissionX.request(this,
    Manifest.permission.CALL_PHONE,
    Manifest.permission.WRITE_EXTERNAL_STORAGE,
    Manifest.permission.READ_CONTACTS) { allGranted, deniedList ->
        if (allGranted) {
        Toast.makeText(this, "All permissions are granted", Toast.LENGTH_SHORT).show()
    } else {
        Toast.makeText(this, "You denied $deniedList", Toast.LENGTH_SHORT).show()
    }
}
 */