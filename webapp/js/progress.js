var app = angular.module( "graphModule", [] )
	
app.controller(
	"graphController",
	function( $scope, graphService ) {
		graphService.initNetwork()
		
		$scope.initNodes = function() {
			var nodeSelect = document.getElementById('nodeFilter').value
			
			graphService.showGraph(nodeSelect)
		}
	}
)

app.service(
	"graphService",
	function( $http, $q ) {
		
		var nodes, edges
		var nodesJson = {"sb14refisrv.p":["sbsprsrv.i","migrtrac.i","sb14exelsrv.p","sb14calvaca.p","ps_sim_s_l_bu_renommeflxtmp.p","ps_sim_s_l_bu_renommeflxtmp.p","ps_sim_s_l_bu_renommeflxtmp.p","sb14sepajucor.p","sb14cumajucor.p","sb14cumajucor.p"],"sb14ann1srv.p":["sbsprsrv.i"],"sb062calptges.p":["sb14topabsence.p"],"sb97del.i":[],"sb14cpte.w":["hr99mbdf.i","sbsprclt.i","sbstatus.p","sbstatus.p","sb99prme.p","sbstatus.p"],"sb01rtdc.p":[],"sb14pra3.w":["hr99mbdf.i","sbsprclt.i","hr99mbdi.i","sbstatus.p","sb99prme.p","inislide.i","modslide.i","hr99mben.i","sbstatus.p"],"sb97exp0.p":["hr99ptmf.i","sbonglet.p"],"sb01rtcr.p":[],"sb97org4.p":["sb97org0.p"],"sb14pgco.w":["hr99mbdf.i","sbsprclt.i","sbstatus.p","sb99prme.p","inislide.i","modslide.i","sbstatus.p"],"sb01rtlg.p":[],"sb06mutu.i":[],"sb97gvt1.p":["sb97gvt0.p"],"sb14dele.p":[],"sb97pag6.p":["sb97pag0.p"],"sb06sal.i":[],"sb99prme.p":[],"sb97agt5.p":["sb97agt0.p"],"bt99dvar.i":[],"sb26gestsrv.p":["sbsprsrv.i","migrtrac.i"],"simusrv.p":["sbsprsrv.i","sbversio.p"],"sb97org5.p":["sb97org0.p"],"sb15rectsrv.p":["sbsprsrv.i","migrtrac.i","sb061cal.p"],"sb14mdacsrv.p":["sbsprsrv.i"],"sb14envl.w":["hr99mbdf.i","sbsprclt.i","sbstatus.p","sbstatus.p","sb99prme.p","sbstatus.p","iniSlide.i","inislide.i","modslide.i"],"sb06saco.i":[],"hr99mbdi.i":[],"sbaprese.i":[],"sb09simp.p":[],"sb14pgpr.w":["hr99mbdf.i","hr99mben.i","hr99mben.i","hr99mbdi.i","sbstatus.p","sb99prme.p","inislide.i","modslide.i","sbstatus.p"],"sb14slagsrv.p":["sbsprsrv.i","ps_grh_e_c_ag_marquageagdeltasim.p"],"sb01raze.p":["sb01remant.p"],"sb14spfr.w":["hr99mbdf.i","sbsprclt.i","sbstatus.p","sb99prme.p","inislide.i","modslide.i","sbstatus.p"],"sb14pra3srv.p":["sbsprsrv.i","sb14topabsence.p","sb061cal.p"],"sb06inter.i":[],"sb57prim.p":[],"sb06efco.i":[],"sb01exor.p":[],"sb14rect.w":["hr99mbdf.i","hr99ptmf.i","sbsprclt.i","sbstatus.p","sbstatus.p","sb14slag.w"],"sb14envlsrv.p":["sbsprsrv.i","migrtrac.i"],"sb14pra2.w":["hr99mbdf.i","sbsprclt.i","hr99mbdi.i","sbstatus.p","sb99prme.p","inislide.i","modslide.i","hr99mben.i","sbstatus.p"],"sb97exp1.p":["sb97exp0.p"],"sb14pconsrv.p":["sbsprsrv.i","migrtrac.i"],"sb97org3.p":["sb97org0.p"],"sb14extr.w":["sbsprclt.i","sb14slag.w"],"sb97gvt2.p":["sb97gvt0.p"],"sb06vaff.i":[],"sb15rect.p":[],"sb97agt4.p":["sb97agt0.p"],"sb09sima.p":["sb01remant.p"],"sb06cp.i":[],"sb01rtch.p":[],"shisb6702magic3150.p":[],"sb97sim6.p":["sb97sim0.p"],"sbaafges.i":[],"sb14tptrsrv.p":["sbsprsrv.i","migrtrac.i","sb14topabsence.p","sb061cal.p","ps_grh_e_c_ag_marquageagdeltasim.p"],"sbaafser.i":[],"sb26servsrv.p":["sbsprsrv.i","migrtrac.i"],"sb14extrsrv.p":["sbsprsrv.i","migrtrac.i","sb01nagt.p","sb01page.p","sb01page.p","sb01exor.p","sb01padi.p","sb061cal.p","sb01raze.p","sb10extr.p","sb01raze.p","sb01stpr.p","sb01stre.p","sb57stpr.p","sb57prim.p","sb01tptr.p","sb01affe.p","sb01afge.p","sb01afse.p","sb01attr.p","sb061cal.p","ps_grh_e_c_ag_marquageagdeltasim.p","sb10extr.p","sb061cal.p"],"sb14topabsence.p":[],"sb97pra0.p":["hr99ptmf.i","sbonglet.p"],"sb02edmg.w":["bt99dvar.i"],"sb97pag7.p":["sb97pag0.p"],"sb14simu.w":["sbsprclt.i","sbstatus.p"],"sb14exel.p":["bt99dvar.i","sbsprclt.i"],"sb14ann1.w":["hr99mbdf.i","sbsprclt.i","sbstatus.p","sbstatus.p","sb99prme.p","sbstatus.p","sbstatus.p","iniSlide.i","inislide.i"],"sb06plaf.i":[],"sb01idag.p":["ps_mu_majsitpe.i","sb01remant.p"],"sb06lfrss.i":[],"sb26serv.w":["sbsprclt.i"],"sb01coti.p":[],"sb97str1.p":["sb97str0.p"],"sb06coen.i":[],"sb06prorata.i":[],"sb06elemcal.i":[],"sb97exp2.p":["sb97exp0.p"],"exclusivenowait.i":[],"sb14attrsrv.p":["sbsprsrv.i","sb14topabsence.p","sb061cal.p","ps_grh_e_c_ag_marquageagdeltasim.p"],"sb09sima.i":[],"sb06txsa.i":[],"sbsprsrvdbgrh.p":[],"sblistagtsrv.p":["sbsprsrv.i","migrtrac.i"],"sb97pag4.p":["sb97pag0.p"],"sb14reinsrv.p":["sbsprsrv.i","migrtrac.i"],"sb061cal.p":["sb06dfsv.i","sb06elemcal.i","sb06caldiv.i","sb06prorata.i","sb06coefgapv.i","sb06sal.i","sb06cp.i","sb06primdiv.i","sb06indemndiv.i","sb06indemnlic.i","sb06res641.i","sb06raz.i","sb062calptges.p","sb062calptges.p","sb57vcal.p","sb57vcal.p","sb061ca2.p","sb14topabsence.p","sb06ptho.i","sb06prre.i","sb06prpo.i","sb06prpt.i","sb06prpa.i","sb06prfr.i","sb06sasp.i","sb06sasr.i","sb06sahc.i","sb06indi.i","sb06agdi.i","sb06prtr.i","sb06saco.i","sb06plaf.i","sb06coss.i","sb06inter.i","sb06mutu.i","sb06cppo.i","sb06coen.i","sb06fnal.i","sb06ttrc.i","sb06asse.i","sb06txsa.i","sb06efco.i","sb06form.i","sb06rest.i","sb06mdctr.i"],"sb97agt3.p":["sb97agt0.p"],"sb14intesrv.p":["sbsprsrv.i","migrtrac.i"],"sb06coss.i":["sb06lfrss.i"],"sb14rept.w":["hr99mbdf.i","sbsprclt.i","sbstatus.p","sbstatus.p","sb99prme.p","inislide.i","modslide.i","hr99mben.i","sbstatus.p"],"sb14corr.w":["hr99mbdf.i","sbsprclt.i","sb14ajucor.w","sbstatus.p","sb99prme.p","inislide.i","modslide.i","hr99mben.i","sbstatus.p"],"ps_sim_s_l_bu_renommeflxtmp.p":[],"ps_sim_s_l_pp_salreficp.p":[],"sb97int1.p":["sb97int0.p"],"sb14exelsrv.p":["sbsprsrv.i","migrtrac.i","sb14sepajucor.p","sb14cumajucor.p","sb14cumajucor.p","sb14sepajucor.p","sb14cumajucor.p","sb14cumajucor.p","sb14calvaca.p"],"sb97sim5.p":["sb97sim0.p"],"sb14sepajucor.p":[],"sb59stpr.p":[],"sb14calvaca.p":[],"migrtrac.i":[],"sb14magi.p":["bt99dvar.i","sbsprclt.i"],"modslide.i":[],"sb14attr.w":["hr99mbdf.i","sbsprclt.i","hr99mbdi.i","sbstatus.p","sb99prme.p","inislide.i","modslide.i","hr99mben.i","sbstatus.p"],"sb14adac.w":["hr99mbdf.i","hr99ptmf.i","sbsprclt.i","sbstatus.p","sb99prme.p"],"sb01nagt.p":["sb10extr.p","ps_grh_e_c_ag_marquageagdeltasim.p","sb061cal.p","ps_grh_e_c_ag_marquageagdeltasim.p","sb061cal.p"],"sb15duagsrv.p":["sbsprsrv.i","migrtrac.i","sb061cal.p","ps_grh_e_c_ag_marquageagdeltasim.p"],"sb01rtyc.i":[],"sb06form.i":[],"sb57stpr.p":[],"sb97str0.p":["sbonglet.p"],"sb14tptr.w":["hr99mbdf.i","sbsprclt.i","sbstatus.p","sb99prme.p","inislide.i","modslide.i","sbstatus.p"],"sb14pgtasrv.p":["sbsprsrv.i","migrtrac.i"],"sb00bare.p":[],"sb01afse.p":[],"sbasipro.i":[],"sb97gvt0.p":["hr99ptmf.i","sbonglet.p"],"sb14cumajucor.p":[],"sb14spfrsrv.p":["sbsprsrv.i","sb14topabsence.p","ps_sim_honemplo.p","sb061cal.p","ps_grh_e_c_ag_marquageagdeltasim.p","sb061cal.p","ps_grh_e_c_ag_marquageagdeltasim.p"],"sb97pag5.p":["sb97pag0.p"],"sb97agt2.p":["sb97agt0.p"],"sb14servic.w":["hr99mbdf.i","sbsprclt.i","hr99mbdi.i","sbstatus.p","sb99prme.p","hr99mben.i","sbstatus.p"],"sb14pra2srv.p":["sbsprsrv.i","sb14topabsence.p","sb061cal.p"],"sb97int0.p":["sbonglet.p"],"sb97org6.p":["sb97org0.p"],"sb97sim4.p":["sb97sim0.p"],"sbasitpe.i":[],"sbjouvre.i":[],"sbversio.p":[],"sb14rectsrv.p":["sbsprsrv.i","ps_sim_s_l_bu_renommeflxtmp.p","sb10extr.p","sb14dele.p","sb14topabsence.p"],"sb97cogl.w":["hr99mbdf.i","sbstatus.p","sb99prme.p","inislide.i","modslide.i","sbstatus.p"],"sb15duag.w":["sbsprclt.i"],"sbstatus.p":["sbsprclt.i"],"sb14fiag.p":["sb02edme.w"],"sb14inte-bis.p":[],"int_exel_pchmtr.p":[],"sb14reco.w":["hr99mbdf.i","sbsprclt.i","sbstatus.p","sb99prme.p","inislide.i","modslide.i","hr99mben.i","sbstatus.p"],"sb06caldiv.i":[],"start.p":[],"sbstatussrv.p":["sbsprsrv.i","migrtrac.i"],"sb14patr.w":["hr99mbdf.i","sbsprclt.i","hr99mbdi.i","hr99mbdi.i","sbstatus.p","sb99prme.p","inislide.i","modslide.i","hr99mben.i","sbstatus.p"],"sb14pgcosrv.p":["sbsprsrv.i","migrtrac.i"],"ps_sim_honemplo.p":[],"sb97org0.p":["hr99ptmf.i","sbonglet.p"],"ps_paie_s_l_pp_cumulgratant.p":[],"sb14servicsrv.p":["sbsprsrv.i"],"sb97ajc1.p":["sb97ajc0.p"],"sbsprclt.p":["separateurs.i","hlp.p","hlp.p"],"sb14ajussrv.p":["sbsprsrv.i"],"sbatattr.i":[],"ps_mu_majsitpe.i":[],"sb14pcon.w":["bt99dvar.i","hr99mbdf.i","hr99ptmf.i","sbsprclt.i","sbstatus.p","sb99prme.p","sbstatus.p"],"sb14ajucorsrv.p":["sbsprsrv.i"],"sbsprclt.i":["separateurs.i"],"sb97agt1.p":["sb97agt0.p"],"sb14jouv.w":["hr99mbdf.i","sbstatus.p","sb99prme.p","sbstatus.p"],"sb97pag2.p":["sb97pag0.p"],"sb97sim3.p":["sb97sim0.p"],"sb14gestio.w":["hr99mbdf.i","sbsprclt.i","hr99mbdi.i","sbstatus.p","sb99prme.p","hr99mben.i","sbstatus.p"],"sbapriag.i":[],"sb01rtin.p":[],"simu.p":["gx00dnew.i","sbsprclt.i","sb00bare.p"],"sb14init.p":[],"sb97pra3.p":["sb97pra0.p"],"sb06fnal.i":[],"sb01page.p":[],"sbligneinterv.p":[],"sb14jouvsrv.p":["sbsprsrv.i","migrtrac.i"],"sb06raz.i":[],"sb14mdac.w":["hr99mbdf.i","sbsprclt.i","sbstatus.p","sb99prme.p","inislide.i","modslide.i","hr99mben.i","hr99mben.i","hr99mbdi.i","hr99mbdi.i"],"sb14adacsrv.p":["sbsprsrv.i","sb14adac_accesbdgrh.p"],"sb14magisrv.p":["sbsprsrv.i","ps_sim_s_l_bu_renommeflxtmp.p","sb062calptges.p","sb062calptges.p","sb57vcal.p","sb062calptges.p","sb14magi_accesbdgrh.p","bx00empreinte.p","bx00empreinte.p","sb14ini2.p","sb10extr.p","sb061cal.p","sb14ini2.p","sb061cal.p"],"sb97str2.p":["sb97str0.p"],"pgminterv.p":[],"sb14cptesrv.p":["sbsprsrv.i","migrtrac.i"],"sb01padi.p":[],"sb01afge.p":[],"sb97coglsrv.p":["sbsprsrv.i","sb061cal.p"],"separateurs.i":[],"sb14afgesrv.p":["sbsprsrv.i","sb14topabsence.p","sb061cal.p","ps_grh_e_c_ag_marquageagdeltasim.p"],"sb14afge.w":["hr99mbdf.i","sbsprclt.i","hr99mbdi.i","sbstatus.p","sb99prme.p","inislide.i","modslide.i","hr99mben.i","sbstatus.p"],"sb97agt0.p":["sbonglet.p"],"sb97pag3.p":["sb97pag0.p"],"shisb6703sim.p":["bt99dvar.i","ps_sim_s_l_bu_renommeflxtmp.p","ps_sim_s_l_bu_renommeflxtmp.p","sb062calptges.p","sb062calptges.p","sb57vcal.p","ps_sim_s_l_bu_renommeflxtmp.p","sb01remant.p","ps_sim_s_l_bu_renommeflxtmp.p","ps_sim_s_l_bu_renommeflxtmp.p","ps_sim_s_l_bu_renommeflxtmp.p"],"sb14adac_accesbdgrh.p":[],"sb06indemnlic.i":[],"sb97sim2.p":["sb97sim0.p"],"sb14inte.w":["hr99ptmf.i","sbsprclt.i","sbstatus.p"],"sb14afsesrv.p":["sbsprsrv.i","sb14topabsence.p","sb061cal.p","ps_grh_e_c_ag_marquageagdeltasim.p"],"hr99mbdf.i":[],"sb97pra4.p":["sb97pra0.p"],"sb01remant.p":["ps_sim_s_l_pp_salreficp.p","ps_paie_s_l_pp_cumulgratant.p"],"sb14rein.w":["hr99mbdf.i","sbsprclt.i","sbstatus.p","sb99prme.p","inislide.i","modslide.i","hr99mben.i","sbstatus.p"],"sb06dfsv.i":[],"sb14gestiosrv.p":["sbsprsrv.i"],"sb14recosrv.p":["sbsprsrv.i","migrtrac.i","sb14calvaca.p","sb14sepajucor.p","sb14cumajucor.p","sb14cumajucor.p"],"sb14ajucor.w":["sbsprclt.i"],"sb97org2.p":["sb97org0.p"],"sbasiprr.i":[],"inislide.i":[],"sb14vacasrv.p":["sbsprsrv.i","migrtrac.i"],"sb06res641.i":[],"sb97gvt3.p":["sb97gvt0.p"],"hr99ttdf.i":[],"sb14pgpl.w":["hr99mbdf.i","hr99mben.i","hr99mben.i","hr99mbdi.i","sbstatus.p","sb99prme.p","inislide.i","modslide.i","sbstatus.p"],"sb14ajus.w":["hr99mbdf.i","sbsprclt.i","sb14ajucor.w","sbstatus.p","sb99prme.p","inislide.i","modslide.i","hr99mben.i","sbstatus.p"],"sb14reptsrv.p":["sbsprsrv.i","migrtrac.i"],"sb14refi.w":["hr99mbdf.i","bt99dvar.i","sbsprclt.i","sb14slag.w","sb14slag.w","sb14exel.p","sbstatus.p","sb99prme.p","inislide.i","modslide.i","sbstatus.p"],"sb14agen.w":["hr99mbdf.i","sbsprclt.i","hr99mbdi.i","sb15duag.w","sbstatus.p","sb99prme.p","inislide.i","modslide.i","sbstatus.p","sb14slag.w"],"sb97pag0.p":["sbonglet.p"],"sb97sim1.p":["sb97sim0.p"],"sb01stre.p":[],"sb14vaca.w":["hr99mbdf.i","sbsprclt.i","sbstatus.p","sbstatus.p","sb99prme.p","sbstatus.p","iniSlide.i","inislide.i"],"sb06indemndiv.i":[],"sblistagt.w":["sbsprclt.i"],"sb06coefgapv.i":[],"sb06cppo.i":[],"sb01stpr.p":[],"sb97pra1.p":["sb97pra0.p"],"sb01tyco.i":["sb99coef.p"],"sbatpstr.i":[],"sb01tptr.p":["sb01rtch.p","sb01rtdc.p","sb01rtlg.p","sb01rtlg.p","sb01rtlg.p"],"sb06ttrc.i":[],"sb14pgprsrv.p":["sbsprsrv.i"],"sb14patrsrv.p":["sbsprsrv.i"],"sbsprsrv.i":["separateurs.i","hr99ptmf.i","bt99dvar.i"],"sbsprsrv.p":["separateurs.i","sbsprsrvdbgrh.p"],"sb14slag.w":["sbstatus.p","sbstatus.p","sb26gest.w"],"sb14pgta.w":["hr99mbdf.i","sbsprclt.i","sbstatus.p","sb99prme.p","sbstatus.p","inislide.i"],"sb14magi_accesbdgrh.p":[],"sb14pgplsrv.p":["sbsprsrv.i"],"sb97org1.p":["sb97org0.p"],"sb14afse.w":["hr99mbdf.i","sbsprclt.i","hr99mbdi.i","sbstatus.p","sb99prme.p","inislide.i","modslide.i","hr99mben.i","sbstatus.p"],"sb97ajc0.p":["sbonglet.p"],"sb01attr.p":[],"sb14corrsrv.p":["sbsprsrv.i"],"sb06asse.i":[],"sb01rtre.p":[],"sb97gvt4.p":["sb97gvt0.p"],"sb26gest.w":["sbsprclt.i"],"sbonglet.p":[],"sb06rest.i":[],"sb97pag1.p":["sb97pag0.p"],"sb14elmsal.p":[],"sb06primdiv.i":[],"sb97agt6.p":["sb97agt0.p"],"ps_grh_e_c_ag_marquageagdeltasim.p":[],"hr99ptmf.i":[],"sb97sim0.p":["sbonglet.p"],"sb14simusrv.p":["sbsprsrv.i","migrtrac.i","sb09sima.p","sb09simp.p","sb59stpr.p","sb061cal.p","sb14ini2.p"],"hr99mben.i":[],"sb99coef.p":[],"sb14agensrv.p":["sbsprsrv.i","sb061cal.p","ps_grh_e_c_ag_marquageagdeltasim.p","sb061cal.p","ps_grh_e_c_ag_marquageagdeltasim.p","sb14dele.p","ps_grh_e_c_ag_marquageagdeltasim.p"],"sb06mdctr.i":[],"sb14ini2.p":[],"sb14initsrv.p":["sbsprsrv.i","migrtrac.i","sb14dele.p","sb14ini2.p","sb14ini2.p","sb14ini2.p"],"sb97pra2.p":["sb97pra0.p"],"sb10extr.p":["sb01padi.p","sb01idag.p","sb01stpr.p","sb01stre.p","sb57stpr.p","sb57prim.p","sb01tptr.p","sb01coti.p","sb01affe.p","sb01afge.p","sb01afse.p","sb01attr.p","sb01page.p","sb01exor.p","sb14dele.p","sb14ini2.p"]};

		var baseUrl = "http://localhost:9080"
		
		// Return public API.
		return({
			getData: getData,
			postData: postData,
			initNetwork: initNetwork,
			showGraph: showGraph
		})
		
		function initNetwork() {
			//init nodes & edges
			nodes = new vis.DataSet([])
			edges = new vis.DataSet([])

			// create a network
			var container = document.getElementById('progressGraph')
			var data = {
				nodes: nodes,
				edges: edges
			}
			var options = {}
			var network = new vis.Network(container, data, options)
			network.on("selectNode", function (params) {
				var filter = nodes._data[params.nodes[0]].label
				showGraph(filter)
		    });
		}
		
		function showGraph(nodeSelect){
			nodes.clear()
			edges.clear()
			var nodesIdx = {};
			
			var i = 0;
			var keys = Object.keys(nodesJson).filter(function(e){
				return nodeSelect == "" || nodeSelect == e || (nodesJson[e].indexOf(nodeSelect) > 1)
			})
			keys.forEach(function(from){
				if (!( from in nodesIdx)) {
					nodesIdx[from] = i++
					addNode(nodesIdx[from], from)
				}
				
				var depAdded = {}
				
				var deps = nodesJson[from]
				deps.forEach(function(to) {
					if(!(to in depAdded)){
						if (! (to in nodesIdx)) {
							nodesIdx[to] = i++
							addNode(nodesIdx[to], to)
						}
						addEdge(nodesIdx[from], nodesIdx[to])
						depAdded[to] = true
					}
				})
			})
			console.log("end graph generation")
		}
		
		function getData(url) {
			var request = $http({ method: "GET", url: baseUrl + url, params: {}, data: {} })
			return( request.then( handleSuccess, handleError ) )
		}
		
		function postData(url, data){
			var request = $http({ method: "POST", url: baseUrl + url, params: {}, data: data })
			return( request.then( handleSuccess, handleError ) )
		}
		
		//*Privates*//
		function stopNetwork() {
			if (network !== null) {
				network.destroy()
				network = null
			}
		}
		
		function addNode(nodeId, label) {
			nodes.add({id:nodeId, label:label})
		}
		
		function addEdge(fromId, toId) {
			edges.add({from: fromId, to: toId, arrows:'to'})
		}
		
		function handleError( response ) {
			console.log("handle error !")
			if ( ! angular.isObject( response.data ) || !response.data.message ) {
				return( $q.reject( "An unknown error occurred." ) )
			}
			else {
				return( $q.reject( response.data.message ) )
			}
		}
		
		function handleSuccess( response ) {
			console.log("handle success !")
			return( response.data )
		}
})