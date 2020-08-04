<template>
  <div class="container-fluid m-0 p-0" style="height:100vh;">
    <div class="row">
      <div class="col-3">
        <Sidebar style="height: 100vh;"/>
      </div>
      <div class="col-9">
        <Navbar2/>
        <router-view/>
      </div>
      <Footer/>
    </div>
  </div>
</template>

<script>
import Navbar2 from "@/components/Navbar2.vue"
import Sidebar from "@/components/Sidebar.vue"
import Footer from "@/components/Footer.vue"


export default {
  name : 'Contents',
  components : {
    Sidebar,
    Navbar2,
    Footer,
  }

}
</script>

<style>

</style>