package company18.mctank.forms;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class CustomerInfoUpdateFormUnitTest {

	public CustomerInfoUpdateForm testForm =new CustomerInfoUpdateForm("Bob","Marley", "test@mail.de", "080032168" , 80001010l);


    @Test
    void getFirstname() {
		assertThat(testForm.getFirstname().equals("Bob")).isTrue();
    }

    @Test
    void getLastname() {
		assertThat(testForm.getLastname().equals("Marley")).isTrue();
	}

    @Test
    void getEmail() {
		assertThat(testForm.getEmail().equals("test@mail.de")).isTrue();
	}

    @Test
    void getMobile() {
		assertThat(testForm.getMobile().equals("080032168")).isTrue();
	}

    @Test
    void getId() {
		assertThat(testForm.getId() == 80001010l).isTrue();
	}
}