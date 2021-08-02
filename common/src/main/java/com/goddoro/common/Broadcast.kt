package com.goddoro.common

import com.goddoro.common.data.model.Academy
import com.goddoro.common.data.model.Location
import io.reactivex.subjects.PublishSubject

object Broadcast {

    val eventUploadBroadcast : PublishSubject<String> = PublishSubject.create()


    val findAddressBroadcast : PublishSubject<Location> = PublishSubject.create()

    val bottomIndexChangeBroadcast : PublishSubject<Int> = PublishSubject.create()

    val profileGoTopBroadcast : PublishSubject<Unit> = PublishSubject.create()

    val profileImageUpdateBroadcast : PublishSubject<Unit> = PublishSubject.create()

    val pickAcademyBroadcast : PublishSubject<Academy> = PublishSubject.create()

    val registerAcademyCompleteBroadcast : PublishSubject<Unit> = PublishSubject.create()
}