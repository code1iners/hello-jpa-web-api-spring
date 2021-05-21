package jpabook.jpashopreview;

import jpabook.jpashopreview.domain.TestModel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class LombokTest {

    @Test
    void getterAndSetter() {

        String text = "hello lombok";
        TestModel d = new TestModel();
        d.setData(text);

        Assertions.assertThat(d.getData()).isEqualTo(text);
    }
}
