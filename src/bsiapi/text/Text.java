package bsiapi.text;

/**
 * Класс, описывающий информацию о тексте.
 *
 * @author bsi
 */
public class Text {
    private final String text;
    private final double rotationAngle;
    private final double sizeMultiplier;

    /**
     * Конструктор класса.
     *
     * @param text содержимое текста.
     */
    public Text(final String text) {
        this(text, 0, 1);
    }


    /**
     * Конструктор класса.
     *
     * @param text содержимое текста.
     * @param rotationAngle угол поворота.
     * @param sizeMultiplier множитель размера.
     */
    public Text(final String text, final double rotationAngle, final double sizeMultiplier) {
        this.text = text;
        this.rotationAngle = rotationAngle;
        this.sizeMultiplier = sizeMultiplier;
    }

    /**
     * Функция получения содержимого текста.
     *
     * @return угол поворота.
     */
    public String getText() {
        return text;
    }

    /**
     * Функция получения угла поворота.
     *
     * @return угол поворота.
     */
    public double getRotationAngle() {
        return rotationAngle;
    }

    /**
     * Функция получения множителя размера.
     *
     * @return множитель размера.
     */
    public double getSizeMultiplier() {
        return sizeMultiplier;
    }
}
