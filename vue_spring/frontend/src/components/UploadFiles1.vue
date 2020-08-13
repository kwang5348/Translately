<template>
  <div role="group">
      <label for="input-live"></label>
      <b-form-input
        id="input-live"
        v-model="link"
        :state="LinkState"
        aria-describedby="input-live-help input-live-feedback"
        placeholder="예시) https://www.youtube.com/watch?v="
        trim
      ></b-form-input>

      <b-button v-b-modal.show-btn class="uploadbtn btn btn-warning" 
        @click="$bvModal.show('bv-modal-example')">
          자막 생성 시작
        </b-button>

      <!-- <b-form-text id="input-live-help">유투브 영상 링크를 입력해주세요.</b-form-text> -->
    </div>
</template>

<script>
export default {
  name: "upload-files1",

}
</script>

<style>
.form-control {
    display: block;
    width: 50px;
}

.uploadbtn {
  margin: 10px 0px;
}

</style>