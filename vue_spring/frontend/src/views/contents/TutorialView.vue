<template>
    <div class="wrapper">
        <!-- Sidebar  -->
        
        <!-- Page Content  -->
        <div class="img mb-5" style="margin-top: 70px;">
            <img src='../../../src/img/head-tutorial.png' alt="튜토리얼 타이틀" height="80"/>
        </div>
        <div class="img mb-5" style="margin-top: 210px; margin-left: -580px">
            <!-- <img src='../../../src/img/prepare.png' alt="준비중 로고" height="320"/> -->
        </div>
        <!-- <?
        현재 언어 : {$site_lan}<br>
        <a href = "javascript :lan_change('kr')"> <span cond = "$ site_lan == 'kr'" > <strong> 한국어 </strong> </span> <span  cond = "$ site_lan! = 'kr' "> 한국어 </span> </a> |
        <a href = "javascript :lan_change('en')"> <span cond = "$ site_lan == 'en'" > <strong> 영어 </strong> </span> <span  cond = "$ site_lan! = 'en' "> 영어 </span> </a> |
        <a href = "javascript :lan_change('jp')"> <span cond = "$ site_lan == 'jp'" > <strong> 일어 </strong> </span> <span  cond = "$ site_lan! = 'jp' "> 일어 </span> </a> |
        <a href = "javascript :lan_change('cn')"> <span cond = "$ site_lan == 'cn'" > <strong> 중국어 </strong> </span> <span  cond = "$ site_lan! = 'cn' "> 중국어 </span> </a> |
        <br>
        또는 셀렉트 박스로도 구현 할수 있습니다.

        <select onChange = "lan_change (this.value)" >
        <option value = 'en' {@if ($ site_lan == 'en') echo "selected" @} > 영어 </option>   
        <option value = 'jp' {@if ($ site_lan == 'jp') echo "selected" @} > 일어 </option>   
        <option value = 'cn' {@if ($ site_lan == 'cn') echo "selected" @} > 중국어 </option>   
        <option value = 'kr' {@if ($ site_lan == 'kr') echo "selected" @} > 한국어 </option>   
        </select> <br> <br>
        <hr> <br>
        <br>
        아래와 같이 data-lan 을 사용하여 자동으로 번역되게 할 수 있습니다. <br>

        ([기본정보관리 > 기타운영정보설정 > 자동 번역 관리] 에서 변환 작업 필요)<br>
        <span data-lan="kr" style="color:#0066ff">자동 번역 됩니다.</span><br>
        <br>
        <br>
        또는, cond (if)를 사용하여 언어별로 직접 문장을 작성 할 수 있습니다.<br>
        <div  cond = "$ site_lan == 'en'" >

        안녕하세요. 영어.
        </div>
        <div  cond = "$ site_lan == 'kr'" > 안녕하세요. 한국어 입니다.</div>
        <div  cond = "$ site_lan == 'jp'" > 안녕하세요. 일본어입니다. </div>
        <div  cond = "$ site_lan == 'cn'" > 안녕하세요. 에?.</div>
        ?> -->
    </div>

</template>

<script>

export default {
  name: 'TutorialView',
  data() {
    return {
      articles: []
    }
  },
}
</script>

<style>

</style>