package com.cwj.we.game.puzzle;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 图片切片类
 * Data：2016/9/11-19:53
 * Blog：www.qiuchengjia.cn
 * Author: qiu
 */
public class ImageSplitter {
    /**
     * 将图片切成 piece * piece 块
     * @param bitmap the bitmap
     * @param piece  the piece
     * @return the list
     * @author qiu  博客：www.qiuchengjia.cn 时间：2016-09-11
     */
    public static List<ImagePiece> split(Bitmap bitmap, int piece){

        List<ImagePiece> pieces = new ArrayList<ImagePiece>(piece * piece);

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Log.e("TAG", "bitmap Width = " + width + " , height = " + height);
        int pieceWidth = Math.min(width, height) / piece;
        for (int i = 0; i < piece; i++){
            for (int j = 0; j < piece; j++){
                ImagePiece imagePiece = new ImagePiece();
                imagePiece.index = j + i * piece;
                int xValue = j * pieceWidth;
                int yValue = i * pieceWidth;

                imagePiece.bitmap = Bitmap.createBitmap(bitmap, xValue, yValue,
                        pieceWidth, pieceWidth);
                pieces.add(imagePiece);
            }
        }
        return pieces;
    }
}
