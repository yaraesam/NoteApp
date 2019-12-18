package com.yara.noteapp.util;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

/**
 * Created by anwar-se on 4/29/2019
 * Email: anwar.dev.96@gmail.com
 */
public class FontsManager {


    public static String SF_PRO_DISPLAY = "SFProDisplay";
    public static String SF_PRO_TEXT = "SFProText";
    public static String SF_AVENIR_NEXT = "AvenirNext";

    public static String STYLE_REGULAR = "Regular";
    public static String STYLE_MEDIUM = "Medium";
    public static String STYLE_BOLD = "Bold";
    public static String STYLE_SEMIBOLD = "Semibold";

    public static String EX_OTF = ".otf";
    public static String EX_TTF = ".ttf";


    public static String getFontFileName(String fontNameNum, String fontStyle) {
        String fontName;
        if (fontNameNum == null)
            fontName = FontsManager.SF_PRO_DISPLAY;
        else if (fontNameNum.equalsIgnoreCase("0x0"))
            fontName = FontsManager.SF_PRO_DISPLAY;
        else if (fontNameNum.equalsIgnoreCase("0x1"))
            fontName = FontsManager.SF_PRO_TEXT;
        else if (fontNameNum.equalsIgnoreCase("0x2"))
            fontName = FontsManager.SF_AVENIR_NEXT;
        else
            fontName = FontsManager.SF_PRO_DISPLAY;

        return fontName + "-" + getFontStyle(fontStyle) + getFontEx(fontNameNum);
    }

    public static String getFontStyle(String fontStyleNum) {
        String fontName;
        if (fontStyleNum == null)
            fontName = FontsManager.STYLE_REGULAR;
        else if (fontStyleNum.equalsIgnoreCase("0x0"))
            fontName = FontsManager.STYLE_REGULAR;
        else if (fontStyleNum.equalsIgnoreCase("0x1"))
            fontName = FontsManager.STYLE_MEDIUM;
        else if (fontStyleNum.equalsIgnoreCase("0x2"))
            fontName = FontsManager.STYLE_BOLD;
        else if (fontStyleNum.equalsIgnoreCase("0x3"))
            fontName = FontsManager.STYLE_SEMIBOLD;
        else
            fontName = FontsManager.STYLE_REGULAR;

        return fontName;
    }

    public static String getFontEx(String fontStyleNum) {
        String fontName;
        if (fontStyleNum == null)
            fontName = FontsManager.EX_OTF;
        else if (fontStyleNum.equalsIgnoreCase("0x0"))
            fontName = FontsManager.EX_OTF;
        else if (fontStyleNum.equalsIgnoreCase("0x1"))
            fontName = FontsManager.EX_TTF;
        else
            fontName = FontsManager.EX_OTF;

        return fontName;
    }

    public static Typeface getTypeFace(Context context, String fontName, String fontStyle) {
        Log.d("TAG", "getTypeFace: fontName:fontStyle: " + fontName + "  " + fontStyle);
        String tpeFaceName = "fonts/" + getFontFileName(fontName, fontStyle);
        Log.d("TAG", "getTypeFace: tpeFaceName " + tpeFaceName);
        Typeface tf;
        try {
            tf = Typeface.createFromAsset(context.getAssets(), tpeFaceName);
        } catch (Exception ex) {
            tf = Typeface.DEFAULT;
        }
        return tf;
    }

}
