package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;


class RateDiscountPolicyTest {

    RateDiscountPolicy rateDiscountPolicy = new RateDiscountPolicy();

    @Test
    @DisplayName("if(vip) = 10% discount")
    void vip_o(){
        //given
        Member member = new Member(1L, "steve", Grade.VIP);
        //when
        int discountPrice = rateDiscountPolicy.discount(member, 1000);
        //then
        assertThat(discountPrice).isEqualTo(100);
    }

    @Test
    @DisplayName("if(!vip) = !discount")
    void vip_x(){
        //given
        Member member = new Member(1L, "steve", Grade.BASIC);
        //when
        int discountPrice = rateDiscountPolicy.discount(member, 1000);
        //then
        assertThat(discountPrice).isEqualTo(100);
    }

}