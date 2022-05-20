<template>
  <button @click="$router.push({name:'manager'})">뒤로가기</button>

  <h5>보관소 매니저</h5>
  <div class="manager-add">
    <div class="mb-3">
      <label for="exampleFormControlInput1" class="form-label">매니저아이디</label>
      <input type="text" v-model="memberId" class="form-control" id="exampleFormControlInput1"
             placeholder="매니저id">
    </div>
    <button class="storage-box-b" @click="CheckMember()">CHECK</button>
    <p v-if="memberIdCheck">가능</p>

    보관소 체크
    <div>
      <labe for="storage-search">보관소</labe>
      <input id="storage-search" type="text" v-model="f" placeholder="보관소명">
      <button>검색</button>

      <div>
        <table>
          <thead>
            <tr>
              <th calspan="2">지역</th>
              <th calspan="2">보관소명</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td></td>
              <td></td>
              <td></td>
              <td><button>선택</button></td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>


    <button v-if="memberIdCheck" @click="postManager">ADD</button>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "InputStorageManager",
  data() {
    return {
      managerList: [],
      memberId: '',
      memberIdCheck: false,
    }
  },
  methods:{
    CheckMember() {
      if (!this.memberId) {
        alert('아이디를 입력하세요')
      } else {
        axios.get('api/checkManager/' + this.memberId)
            .then((res) => {
              console.log(res)
              if (res.data.result == 'ok') {
                console.log('가능')
                alert('가능합니다')
                this.memberIdCheck = true

              } else if (res.data.result == 'overlap') {
                console.log('불가능')
                alert('이미 있습니다')
                this.memberIdCheck = false
              } else {
                console.log('없음')
                alert('입력하신 아이디가 없습니다')
                this.memberIdCheck = false
              }
            })
            .catch((error) => {
              console.log(error)
            })
      }
    },
    postManager() {
      let manager = {
        member: memberId,
        storage: storageCode
      }
      if (this.memberIdCheck) {
        axios.post('api/postManager', manager)
            .then((res) => {
              console.log(res.data.result)
              if (res.data.result === 'ok') {
                console.log('생성')
                alert('추가되었습니다')
                this.clearInput()
                this.memberIdCheck = false
              } else {
                alert('추가 되지 않음')
              }
            }).catch((err) => {
          console.log(err)
        })
      }
    },
    clearInput() {
      this.memberId = ''
    },
  },
}
</script>

<style scoped>

</style>