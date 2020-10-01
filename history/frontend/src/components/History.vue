<template>
	<div class="history">
		<v-card>
			<v-card-title>
				<v-text-field
					v-model="search"
					label="Search"
					single-line
					hide-details
				></v-text-field>
			</v-card-title>
			<v-data-table
				:headers="headers"
				:items="historyRecords"
				:search="search"
			></v-data-table>
		</v-card>
	</div>
</template>

<script>
export default {
	name: 'history',
	props: {
		historyRecords: Array
	},
	data() {
		return {
			search: '',
			headers: [
				{
					text: 'Stock',
					align: 'start',
					value: 'stockName',
				},
				{text: 'Operation Date', value: 'date'},
				{text: 'User', value: 'username'},
				{text: 'Amount', value: 'amount', filterable: false},
				{text: 'Type', value: 'type'},
			]
		};
	},
	methods: {}
};
</script>

<style scoped lang='scss'>

	.history {
		width: 100%;
		height: 100%;
	}

</style>
