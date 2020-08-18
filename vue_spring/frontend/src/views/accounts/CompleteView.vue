<template>
    <div class="wrapper">
        <!-- Sidebar  -->
        
        <!-- Page Content  -->
        <div class="img" style="margin-top: 90px;">
            <img src='../../../src/img/logo-color2.png' alt="로고" height="40"/>
            <p style="font-size: 30px; margin-top: 20px;">회원가입이 <strong>완료</strong> 되었습니다!</p>

            <p>Translately 회원이 되신 것을 환영합니다.<br>
            늘 다양하고 멋진 서비스로 찾아뵐게요.</p>
            <b-card class="mx-auto mt-1" style="max-width: 540px; border: transparent; ">
                <b-table stacked :items="items"></b-table>
            </b-card>
            <p style="font-size: 18px;" class="mt-1">닉네임 등 프로필 정보는 <a href="/mypage">마이 페이지</a>에서 수정할 수 있어요.</p>
            <div class="my-4">
                <b-button class="mx-3" squared size="lg" variant="primary" href="/accounts/login">로그인 화면으로  ></b-button>
                <b-button class="mx-3" squared size="lg" href="/">메인 페이지</b-button>
            </div>

        </div>
    </div>

</template>

<script>

export default {
  name: 'CompleteView',
  data() {
    return {
        fields: ['아이디', '이메일', '이름'],
        items: [
          { 아이디: 'donydony', 이메일: 'Dickerson@gmail.com', 이름: '김도연' },
        ]
    }
  },
}
</script>

<style>

</style>