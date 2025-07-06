package com.chaeros.myartspace.utils.image

fun calculateImageNumber(imageNumber: Int, diff: Int, listCount: Int): Int{
    if(imageNumber+diff<0) return listCount-1
    if(imageNumber+diff>=listCount) return (imageNumber+diff)%listCount
    return imageNumber+diff
}