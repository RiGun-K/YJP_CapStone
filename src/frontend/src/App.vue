<template>
	<div id="app">
		<Header />
<!--		<NavBar />-->
		<router-view />
    <Footer />
	</div>
</template>

<script>
import Header from '@/components/common/Header';
import Footer from '@/components/common/Footer';
import NavBar from '@/components/common/NavBar';

export default {
	name: 'App',
	components: {
    Footer,
		Header,
		// NavBar,
	},
};
</script>

<style lang="scss">
@import 'bootstrap/scss/bootstrap';
@import url('https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap');

html,
body {
	padding: 0;
	margin: 0;
}
#app {
	font-family: 'Nanum Gothic', sans-serif;
	font-weight: 700;
	font-size: 1em;
	-webkit-font-smoothing: antialiased;
	-moz-osx-font-smoothing: grayscale;
	//text-align: center;
	color: #2c3e50;
}
table {
	width: 100%;
	border-collapse: collapse;
}
.wrap {
	width: 100%;
}
.container {
	width: 800px;
	margin: 0 auto;
}
a {
	text-decoration: none;
}
.btn {
	padding: 10px;
	background: #34445c;
	color: #fff;
}
button {
	font-family: 'Nanum Gothic', sans-serif;
	font-weight: 700;
	font-size: 1em;
	// =======
	// html,body{padding:0; margin:0;height: 100%}
	// router-view{
	//   height: 100%;
	// }
	// #app {
	//   height: 100%;
	//   font-family: Avenir, Helvetica, Arial, sans-serif;
	//   -webkit-font-smoothing: antialiased;
	//   -moz-osx-font-smoothing: grayscale;
	//   //text-align: center;
	//   color: #2c3e50;
	// >>>>>>> 6184d9e3cf1aba91bb826f47810834fee612abde
}
</style>
