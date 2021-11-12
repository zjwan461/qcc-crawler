console.log("qcc爬虫")

$(function () {


	// $("#start").click(function () {
	setTimeout(() => {
		start()
		// console.log(getPageData())
		// goToNextPage()
		// getTotalPage();
	}, 1000);


	function getPageData() {
		// let url = window.location.href;
		// // console.log(url)
		let array = new Array();
		// if (url.indexOf("firm") != -1 && url.indexOf(".html") != 0) {
		$(document).find(".touzi-partner-tdcoy").find("a[target='_blank']").css({ "color": "green", "border": "2px solid green" }).each((index, item) => {
			console.log(item.innerText + ":" + item.href);
			let obj = { "name": item.innerText, "href": item.href }

			array.push(obj)
		})
		// }
		return array;
	}

	function gotoFirstSearchResult() {
		var url = $(".ntable-list").children(".frtrt").find('.maininfo')
			.children('.title')
			.css({ "color": "green", "border": "2px solid green" })
			.attr('href')

		if (url) {
			sessionStorage.setItem("status", "page")
			window.location.href = url
		}
	}

	function goToNextPage() {
		console.log("go to next page")
		$('nav .pagination')
			.find('li:contains(>) a')[0]
			// .css({ "color": "green", "border": "2px solid green" })
			.click();
	}

	function getTotalPage() {
		var total = $('nav .pagination').find('li:last').prev().children().eq(0).text();
		if (total.indexOf("...") != -1) {
			total = total.substring(3)
		}
		return total;
	}

	function hasNextPage() {
		if ($('nav .pagination')
			.find('li:contains(>) a')[0])
			return true;
		else
			return false;
	}

	function searchResult(searchText) {
		if (searchText) {
			sessionStorage.setItem("status", "result")
			$("#searchkey").val(searchText);
			$(".index-searchbtn").css({ "color": "green", "border": "2px solid green" }).click();
		}
	}

	function getRootSearchText() {
		return "深圳市引导基金投资有限公司";
	}

	const sleep = (milliseconds) => {
		return new Promise(resolve => setTimeout(resolve, milliseconds))
	}

	// function start() {
	const start = async () => {
		try {
			let status = sessionStorage.getItem("status");
			if (!status) {
				sessionStorage.setItem("status", "search");
				window.location.reload()
			} else if (status == 'search') {
				let searchText = getRootSearchText();
				console.log("开始搜索：" + searchResult)
				searchResult(searchText);
			} else if (status == 'result') {
				console.log("前往搜索结果第一家")
				gotoFirstSearchResult();
			} else if (status == 'page') {
				console.log("装载data中...")
				let pageData = getPageData();
				let total = getTotalPage();
				for (let i = 0; i < total - 1; i++) {
					console.log("第" + (i + 1) + "页");
					goToNextPage();
					await sleep(500);
					console.log(i);
					// console.log(getPageData());
					// pageData.concat(...getPageData());
					// pageData = Object.assign(pageData, ...getPageData());
					pageData = [...pageData, ...getPageData()];
					console.log(pageData.length);
				}
				downFlie(pageData, getRootSearchText())

			}
		} catch (error) {
			console.log(error)
			sessionStorage.setItem("status", "");
		}

	}

	function downFlie(data, filename) {
		// 创建a标签
		var elementA = document.createElement('a');

		//文件的名称为时间戳加文件名后缀
		elementA.download = +new Date() + ".txt";
		elementA.style.display = 'none';

		//生成一个blob二进制数据，内容为json数据
		var blob = new Blob([JSON.stringify(data)]);

		//生成一个指向blob的URL地址，并赋值给a标签的href属性
		elementA.href = URL.createObjectURL(blob);
		document.body.appendChild(elementA);
		elementA.click();
		document.body.removeChild(elementA);
		sessionStorage.setItem("status", "");

	}

})