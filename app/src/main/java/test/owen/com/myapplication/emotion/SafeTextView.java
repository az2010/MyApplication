package test.owen.com.myapplication.emotion;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @author littletli
 *
 */
public class SafeTextView extends TextView {
	
	public SafeTextView(Context context) {
		this(context, null);
	}

	public SafeTextView(Context context, AttributeSet attrs) {
	    super(context, attrs);
	}

	public SafeTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	
	@Override
    public void setText(CharSequence text, BufferType type) {
        try {
            super.setText(text, type);
        } catch (Throwable e) {
            try {
                String replaceText = filterEmoji(getText().toString());
                super.setText(replaceText, type);
            } catch (Throwable error) { // 如果再次异常，直接设置为空
                setText("");
            }
        }
    }


    @Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		try {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		} catch (Throwable e) {
			try {
				String replaceText = filterEmoji(getText().toString());
				setText(replaceText);
				super.onMeasure(widthMeasureSpec, heightMeasureSpec);
			} catch (Throwable error) { // 如果再次异常，直接设置为空
				setText("");
				super.onMeasure(widthMeasureSpec, heightMeasureSpec);
			}
		}
	}
	
	/**
	 * 检测是否有emoji字符
	 * 
	 * @param source
	 * @return 一旦含有返回
	 */
	public static boolean containsEmoji(String source) {

		int len = source.length();

		for (int i = 0; i < len; i++) {
			char code = source.charAt(i);
			if (!isNormalCharacter(code)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * not emoji
	 * 
	 * @param code
	 * @return
	 */
	private static boolean isNormalCharacter(char code) {
		return (code == 0x0) 
		        || (code == 0x9)
		        || (code == 0xA) 
		        || (code == 0xD) 
		        || ((code >= 0x20) && (code <= 0xD7FF))
				|| ((code >= 0xE000) && (code <= 0xFFFD));
	}

	/**
	 * 过滤emoji 或者 其他非文字类型的字符
	 * 
	 * @param source
	 * @return
	 */
	public static String filterEmoji(String source) {
		if (source == null || source.length() <= 0){
			return source;
		}

		if (!containsEmoji(source)) {
			return source;// 如果不包含，直接返回
		}
		StringBuilder buf = null;
		int len = source.length();		
		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);
			if (isNormalCharacter(codePoint)) {
				if (buf == null) {
					buf = new StringBuilder(source.length());
				}
				buf.append(codePoint);
			}
		}

		if (buf == null) {// all 
			return "";
		} else {
			if (buf.length() == len) {
				return source;
			} else {
				return buf.toString();
			}
		}
	}


}
