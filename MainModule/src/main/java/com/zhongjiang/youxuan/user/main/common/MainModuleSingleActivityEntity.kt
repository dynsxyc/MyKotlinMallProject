package com.zhongjiang.youxuan.user.main.common

import android.os.Parcel
import android.os.Parcelable

/**
 * @date on 2019/5/8 11:12
 * @packagename com.zhongjiang.youxuan.user.main.common
 * @author dyn
 * @fileName MainModuleSingleActivityEntity
 * @org com.zhongjiang.youxuan
 * @describe 添加描述
 * @email 583454199@qq.com
 **/
data class MainModuleSingleActivityEntity(var type: MainModuleSingleActivityType, var stringWidth: HashMap<String, String>?, var booleanWidth: HashMap<String, Boolean>?) : Parcelable {
    constructor(source: Parcel) : this(
            MainModuleSingleActivityType.values()[source.readInt()],
            source.readSerializable() as HashMap<String, String>?,
            source.readSerializable() as HashMap<String, Boolean>?
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(type.ordinal)
        writeSerializable(stringWidth)
        writeSerializable(booleanWidth)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<MainModuleSingleActivityEntity> = object : Parcelable.Creator<MainModuleSingleActivityEntity> {
            override fun createFromParcel(source: Parcel): MainModuleSingleActivityEntity = MainModuleSingleActivityEntity(source)
            override fun newArray(size: Int): Array<MainModuleSingleActivityEntity?> = arrayOfNulls(size)
        }
    }
}