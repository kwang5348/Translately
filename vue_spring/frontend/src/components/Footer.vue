<template>
  <div class="footer col align-self-center">
    <footer class="page-footer font-small mdb-color pt-4">

      <div class="container text-center text-md-left">

        <div class="row text-center text-md-left mt-3 pb-5">

          <div class="col-md-6 col-lg-6 col-xl-6 mx-auto mt-3">
            <h5 class="text-uppercase mb-4 font-weight-bold text-white">Translately</h5>
            <p>내가 원하는 영상을 원하는 언어로</p>
            <p>지금 나에게 필요한 영상을 원하는 언어의 자막으로 변환해 보세요.</p>
          </div>

          <hr class="w-100 clearfix d-md-none">

          <div class="col-md-3 col-lg-3 col-xl-3 mx-auto mt-3">
            <h5 class="text-uppercase mb-4 font-weight-bold text-white">사이트 링크</h5>
            <p><a class="text-black" href="/contents/tutorial" style="text-decoration: none;">튜토리얼</a></p>
            <p><a class="text-black" href="/contents/myproject" style="text-decoration: none;">내 프로젝트</a></p>
            <p><a class="text-black" href="/contents/community" style="text-decoration: none;">커뮤니티</a></p>
            <p><a class="text-black" href="/contents/mypage" style="text-decoration: none;">마이 페이지</a></p>
          </div>

          <hr class="w-100 clearfix d-md-none">          
          <hr class="w-100 clearfix d-md-none">

          <div class="col-md-3 col-lg-3 col-xl-3 mx-auto mt-3">
            <h5 class="text-uppercase mb-4 font-weight-bold text-white">연락처</h5>
            <p><i class="fa fa-home mr-3"></i>  SSAFY, 서울 5반, 대한민국</p>
            <p><i class="fa fa-envelope mr-3"></i><a href="mailto:hdp0545@gmail.com" class="text-black"> qna@translately.com </a></p>
            <p><i class="fa fa-phone mr-3"></i> 대표: 홍두표</p>
          </div>
        </div>
        <div class="row text-center text-md-left mt-1 pb-5">
          <div class="col-md-9 col-lg-9 col-xl-9 mx-auto mt-3"></div>
          <div class="col-md-3 col-lg-3 col-xl-3 mx-auto mt-3">
            <img src="../img/logo-footer.png" class="d-block w-100" alt="로고 푸터">
          </div>
        </div>



      </div>
                  
    </footer>

  </div>
</template>

<script>
export default {
  name : 'Footer',
}
</script>

<style scoped>
.footer {
  background: black;
  left: 0;
  font-family: 'InfinitySans-RegularA1'
}

p {
  margin-bottom : 5px;
  font-size: 15px;
  font-family: 'InfinitySans-RegularA1'
}

</style>