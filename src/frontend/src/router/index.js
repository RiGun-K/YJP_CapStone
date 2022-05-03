import {createRouter, createWebHistory} from 'vue-router'


const routes = [
    /////////////////////////////회원관리///////////////////////////////
  {
    path: '/',
    name: 'Main',
    component: () => import('@/components/HelloWorld.vue')
  },
  {
    path: '/signup',
    name: 'SignUp',
    component: () => import('@/components/user/Signup.vue')
  },
  {
    path: '/mAdmin',
    name: 'mAdmin',
    component: () => import('@/components/user/Admin.vue')
  },
  {
    path: '/company',
    name: 'Company',
    component: () => import('@/components/user/Company.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/components/user/Login.vue')
  },
  {
    path: '/memberupdate',
    name: 'MemberData',
    component: () => import('@/components/user/MemberData.vue')
  },
  {
    path: '/horby',
    name: 'Horby',
    component: () => import('@/components/user/Horby.vue')
  },
  {
    path:'/agreement',
    name:'Agreement',
    component: () => import('@/components/user/Agreement.vue')
  },
  {
    path:'/sellerAccept',
    name:'SellerAccept',
    component: () => import('@/components/user/SellerAccept.vue')
  },
  {
    path:'/searchSelect',
    name:'SearchSelect',
    component: () => import('@/components/user/SearchSelect.vue')
  },
  {
    path:'/searchId',
    name:'SearchId',
    component: () => import('@/components/user/IdSearch.vue')
  },
  {
    path:'/searchPass',
    name:'SearchPass',
    component: () => import('@/components/user/PassSearch.vue')
  },

    //////////////////////////////게시판////////////////////////////////////

  {
    path: '/board',
    name: 'Board',
    component: () => import('@/components/board/Board.vue')
  },
  {
    path: '/read',
    name: 'Read',
    component: () => import('@/components/board/Read.vue')
  },
  {
    path: '/create/:contentId?',
    name: 'Create',
    component: () => import('@/components/board/Create.vue')
  },
  {
    path: '/create',
    name: 'Create',
    component: () => import('@/components/board/Create.vue')
  },
  {
    path: '/view/:writer_code',
    name: 'View',
    component: () => import('@/components/board/View.vue')
  },
  {
    path: '/update/:writer_code',
    name: 'Update',
    component: () => import('@/components/board/Update.vue')
  },

    ///////////////////////////////상품관리///////////////////////////////////

  {
    path: '/ProductMain',
    name: 'ProductMain',
    component: () => import('@/components/product/ProductMain.vue')
  },
  {
    path: '/ProductPage',
    name: 'ProductPage',
    component: () => import('@/components/product/ProductPage.vue')
  },
    ////////////////////////////////////////////////////
  {
    path: '/ProductBuy',
    name: 'ProductBuy',
    component: () => import('@/components/product/RegistrationBuy.vue')
  },
  {
    path: '/ProductRental',
    name: 'ProductRental',
    component: () => import('@/components/product/RegistrationRental.vue')
  },
  {
    path: '/ProductCamping',
    name: 'ProductCamping',
    component: () => import('@/components/product/RegistrationCamping.vue')
  },
  {
    path: '/RegistrationCampingDetail/:campingName',
    name: 'RegistrationCampingDetail',
    component: () => import('@/components/product/RegistrationCampingDetail.vue')
  },
    /////////////////////////////////////////////////////////
  {
    path: '/MyProductList',
    name: 'MyProductList',
    component: () => import('@/components/product/MyProductList.vue')
  },
  {
    path: '/MyProductDetail/:menuid',
    name: 'MyProductDetail',
    component: () => import('@/components/product/MyProductDetail.vue'),
    props: true
  },
  {
    path: '/MyProductEdit',
    name: 'MyProductEdit',
    component: () => import('@/components/product/MyProductEdit.vue'),
    props: true,
  },
  {
    path: '/ProductList',
    name: 'ProductList',
    component: () => import('@/components/product/ProductList.vue')
  },
  {
    path: '/ProductDetail/:menuid',
    name: 'productDetail',
    component: () => import('@/components/product/ProductDetail.vue'),
    props: true
  },

    ////////////////////////주문, 결제/////////////////////////////////
  {
    path: '/itemBuy',
    name: 'ItemBuy',
    component: () => import('@/components/cart/ItemBuy/BuyList.vue')
  },
  {
    path: '/itemBuy/buyList/:menuid',
    name: 'BuyDetailList',
    component: () => import('@/components/cart/ItemBuy/BuyDetailList.vue'),
    props: true
  },
  {
    path: '/itemBuy/buyNow/:menuid',
    name: 'BuyNow',
    component: () => import('@/components/cart/ItemBuy/BuyNow.vue'),
    props: true
  },
  {
    path: '/itemBuy/buyComplete',
    name: 'BuyComplete',
    component: () => import('@/components/cart/ItemBuy/BuyComplete.vue')
  },
  {
    path: '/itemShare',
    name: 'ShareList',
    component: () => import('@/components/cart/ItemShare/ShareList.vue')
  },
  {
    path: '/itemShare/shareNow',
    name: 'ShareNow',
    component: () => import('@/components/cart/ItemShare/ShareNow.vue')
  },
  {
    path: '/itemBuy/shareComplete',
    name: 'shareComplete',
    component: () => import('@/components/cart/ItemShare/ShareComplete.vue')
  },
  {
    path: '/infoter',
    name: 'InfoterList',
    component: () => import('@/components/cart/Infoter/InfoterList.vue')
  },
  {
    path: '/infoter/infoterNow',
    name: 'InfoterNow',
    component: () => import('@/components/cart/Infoter/InfoterNow.vue')
  },
  {
    path: '/infoter/infoterComplete',
    name: 'InfoterComplete',
    component: () => import('@/components/cart/Infoter/InfoterNow.vue')
  },
  {
    path: '/cart',
    name: 'CartList',
    component: () => import('@/components/cart/Cart/CartList.vue')
  },
  {
    path: '/cart/buy',
    name: 'BuyCart',
    component: () => import('@/components/cart/Cart/BuyCart.vue')
  },
  {
    path: '/cart/share',
    name: 'ShareCart',
    component: () => import('@/components/cart/Cart/ShareCart.vue')
  },
  {
    path: '/cart/reservation',
    name: 'ReservationCart',
    component: () => import('@/components/cart/Cart/ReservationCart.vue')
  },
    //////////////////////////////보관함관리/////////////////////////////////

  {
    path: "/storageAdmin",
    name: "admin",
    component: ()=> import('@/views/admin/StorageAdminView.vue')
  },
  {
    path: "/storage",
    name: "storage",
    component: ()=> import('@/views/admin/storage/StorageView.vue')
  },
  {
    path: "/manager",
    name: "manager",
    component: ()=> import('@/views/admin/manager/StorageManagerView.vue')
  },
  {
    path: "/storageView",
    name:"userStorage",
    component: ()=> import('@/views/user/UserStorageView.vue')
  },
  {
    path: "/storageView/:storageCode",
    name:"userStorageDetail",
    component: ()=> import('@/components/storageService/user/UserStorageDetail.vue')
  },
  {
    path:"/storageView/:storageCode/paypage",
    name:"storagePay",
    component:()=> import('@/views/user/StoragePayPage.vue')
  },
  {
    path: "/myBox",
    name: "myBox",
    component:()=>import('@/views/user/MyStorageBox.vue')
  },
  {
    path: "/myBox/renewalBox",
    name: "renewalBox",
    component:()=>import('@/components/storageService/user/RenewalBox.vue')
  },
  {
    path: "/myBox/moveBox",
    name: "moveBox",
    component:()=>import('@/components/storageService/user/MoveBox.vue')
  },
  {
    path: "/myBox/moveBox/pay",
    name: "BoxMovePay",
    component:()=>import('@/views/user/BoxMovePay.vue'),
    props: true
  },
  {
    path: "/myBox/repairBox",
    name: "repairBox",
    component:()=>import('@/components/storageService/user/RepairBox.vue')
  },
  {
    path:'/storageManager',
    name:'storageManager',
    component: ()=> import('@/views/manager/ManagerStorage.vue'),
    children:[
      {
        path: ":storage",
        name: "boxDetail",
        component: ()=> import('@/components/storageService/manager/BoxModalDetail.vue')
      }
    ]
  },
  //////////////계획관리//////////////////////
  {
    path: '/TeamManagementPage',
    name: 'teamManage',
    component: () => import('@/components/team/TeamManagementPage'),
  },
  {
    path: '/TeamMember',
    name: 'teamMember',
    component: () => import('@/components/team/TeamMember'),
  },
  {
    path: '/basicPlan',

    component: () => import('@/components/plan/basicPlan'),
  },
  {
    path: '/detailPlan',
    name: 'detailPlan',
    component: () => import('@/components/plan/detailPlan'),
  },
  {
    path: '/selectCampingPlace',
    name: 'selectCampingPlace',
    component: () => import('@/components/plan/selectCampingPlace'),
  },
  {
    path: '/detailPlan/:detailCode',
    name: 'checkDetailPlan',
    component: () => import('@/components/plan/detailPlan'),
  },


]

export const router = createRouter({
  history: createWebHistory(),
  routes
})
