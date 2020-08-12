<template>
  <div class="container-fluid m-0 p-0" style="height:100vh;">
    <div class="row">
      <div class="col-3">
        <Sidebar style="height: 100vh;" @logout="logout"/>
      </div>
      <div class="col-9">
        <Navbar2/>
        <router-view/>
      </div>
    </div>
  </div>
</template>

<script>
import Navbar2 from "@/components/Navbar2.vue"
import Sidebar from "@/components/Sidebar.vue"

export default {
  name : 'Contents',
  components : {
    Sidebar,
    Navbar2,
  },
  methods: {
    logout() {
      this.$emit('logout')
      }
  }

}
</script>

<style>

</style>