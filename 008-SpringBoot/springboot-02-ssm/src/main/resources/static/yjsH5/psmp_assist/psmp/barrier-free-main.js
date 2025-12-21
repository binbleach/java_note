!(function Global () {
  /**
   * 页面引用的 jquery 版本可能与无障碍使用的不一致，所以在这里还原页面的 jquery。
   * pageJquery 来自 barrier-free.js。
   */
  const $ = window.$
  const pageJquery = window.pageJquery

  if ($ !== pageJquery) {
    window.$ = window.jQuery = pageJquery
  }

  const isIE = navigator.userAgent.match(/msie\s*(\d+)|rv:\s*11/i)
  if (window.psmpWorking || isIE && isIE[1] && isIE[1] < 10) return

  let __SupportKeyboard__ = true

  !(function (Classes) {
    const isInFrame = top !== window
    let model = null
    // let model = null
    const winMessage = new Classes.WindowMessage()
    const useIntercept = interceptJquery()
    window.initpsmpTools = initpsmpTools

    // 初始化
    function initpsmpTools (flag) {
      /**
       * 在顶层窗口收集所有子孙窗口的 window 对象及 frame_id，用于多层嵌套窗口时识别窗口
       */
      model = flag || false
      if (isInFrame) {
        const frame_id = Math.random().toString(16)

        winMessage.send(top, 'connect', frame_id).waitReply(function (reply, shouldUsepsmp) {
          shouldUsepsmp && Run()
        })
      } else {
        const frameWindows = {}
        const shouldUsepsmp = ~location.search.indexOf('psmp') && (setCookie('psmp', '1'), true) || !!getCookie('psmp')

        winMessage.addCommandHandler('connect', function (reply, frame_id) {
          frameWindows[frame_id] = reply.target
          reply(shouldUsepsmp)
        })

        shouldUsepsmp ? Run() : addEntrance()
      }
    }

    function interceptJquery () {
      if (!pageJquery) return Function.prototype

      let history = []
      let eventCenter = null

      pageJquery.event.add = intercept('event', pageJquery.event.add)
      // pageJquery.prototype.append = intercept("append", pageJquery.prototype.append)

      return function (eventCenter$) {
        eventCenter = eventCenter$
        history.forEach(function (record) {
          eventCenter.trigger.apply(eventCenter, record)
        })

        history = null
      }

      function intercept (name, method) {
        return function () {
          const args = [].slice.call(arguments)

          eventCenter
            ? eventCenter.trigger(name + '$before', this, args)
            : history.push([name + '$before', this, args])

          const result = method.apply(this, arguments)

          eventCenter
            ? eventCenter.trigger(name + '$after', result, this, args)
            : history.push([name + '$after', result, this, args])

          return result
        }
      }
    }

    function addEntrance () {
      let voice = new Classes.Voice()
      let keyupCapture = new Classes.EventCapture('keyup')
      let shortcut = new Classes.Shortcut(keyupCapture)
      let showReader = false
      const key = 'ctrl+`'

      // TODO 自定义
      onDocReady(function () {
        setCookie('psmp', '1')

        Run(showReader, voice)
        // TODO
        // voice.play('欢迎访问人社公共服务平台', { forcePlay: true, })

        shortcut.remove(key)
        shortcut = keyupCapture = voice = null
      })

      shortcut.set(key, function () {
        showReader = true
        $('#psmp_on').click()
      })
    }

    function Run (showReader, voice) {
      let cssZoomFactor = 1
      let cssScaleFactor = 1
      let pageZoom = 1
      const useCssZoom = !isIE && !navigator.userAgent.match(/mozilla|opera/)
      const psmpTool = isInFrame ? createpsmpTool('zoom', 'magnifyGlass') : createpsmpTool()
      const eventCenter = new Classes.EventCenter()

      window.psmpWorking = true

      interceptNative()
      supportKeyboard()

      onDocReady(function () {
        let workbench = null
        const EventCapture = Classes.EventCapture
        const mouseCapture = new EventCapture('mouseover')
        const focusCapture = new EventCapture('focusin')
        const clickCapture = new EventCapture('click')
        const leaveCapture = new EventCapture('mouseout')
        const moveCapture = new EventCapture('mousemove')
        const keyupCapture = new EventCapture('keyup')

        !(function Main () {
          handlePageSetting(window.psmpPageSetting)
          handlePageSetting(window.psmpPageSetting_1)
          handlePageSetting(window.psmpPageSetting_2)
          setImgCode()

          setTabIndexForContent()
          useIntercept(eventCenter)
          processAriaLabel()

          /**
           * 用于窗口获取顶层窗口的信息及自身在顶层窗口的位置信息
           * 多层嵌套窗口时，会逐层获取并累加
           */
          winMessage.addCommandHandler('frameOutsideInfo', function (reply) {
            const frameElem = getFrameElem(reply.target)
            const outsideInfo = {
              workbench_height: isInFrame ? 0 : workbench.height(),
              scrollTop: getBodyAttribute('scrollTop'),
              height: $(frameElem).height(),
              top: offset(frameElem).top,
            }

            if (!isInFrame) return reply(outsideInfo)

            winMessage.send(parent, 'frameOutsideInfo').waitReply(function (_reply, _outsideInfo) {
              outsideInfo.workbench_height += _outsideInfo.workbench_height
              outsideInfo.scrollTop += _outsideInfo.scrollTop
              outsideInfo.top += _outsideInfo.top

              reply(outsideInfo)
            })
          })

          if (isInFrame) {
            let frame_elem_id = 0
            let outsideInfo = null

            mouseCapture.set('sendMouseCommand', sendEventComand('mouse'))
            focusCapture.set('sendFocusCommand', sendEventComand('focus'))
            clickCapture.set('sendClickCommand', sendEventComand('click'))
            leaveCapture.set('sendLeaveCommand', sendEventComand('leave'))
            moveCapture.set('sendMoveCommand', sendEventComand('move'))
            keyupCapture.set('sendKeyupCommand', sendEventComand('keyup'))

            winMessage.addCommandHandler('extractText', function (reply, elem_id) {
              const elem = getElemById(elem_id)
              if (!elem) return

              extractText(elem).then(reply)
            })

            winMessage.addCommandHandler('getNextElem', function (reply, elem_id) {
              let elem

              if (elem_id) {
                elem = getElemById(elem_id)
                if (!elem) return reply()
              } else if (reply.target.parent === window) {
                /* 从子窗口发出的命令，使用子窗口的 iframe 元素作为参考获取下一个元素 */
                elem = getFrameElem(reply.target)
              } else if (reply.target === parent) {
                /* 从父窗口发出的命令，使用 body 元素的前一个元素作为参考获取下一个元素 */
                elem = document.body.previousSibling
              }

              getNextElem(elem).then(function (nextElemOrFrameInfo) {
                let frameInfo

                if (isFrameInfo(nextElemOrFrameInfo)) {
                  frameInfo = nextElemOrFrameInfo
                } else if (nextElemOrFrameInfo) {
                  frameInfo = genFrameInfo({ elem_id: getElemId(nextElemOrFrameInfo), })
                }

                reply(frameInfo)
              })
            })

            winMessage.addCommandHandler('markPlayingElem', function (reply, elem_id, shouldMark) {
              const elem = getElemById(elem_id)
              if (!elem) return

              markPlayingElem(elem, shouldMark)
            })

            winMessage.addCommandHandler('zoom', function (reply, zoom) {
              psmpTool.zoom.setZoom(zoom)
              $('iframe').toArray().forEach(function (frame) {
                winMessage.send(frame.contentWindow, 'zoom', zoom)
              })
            })

            const updateOutsideInfo = throttle(function () {
              winMessage.send(parent, 'frameOutsideInfo').waitReply(function (reply, _outsideInfo) {
                outsideInfo = _outsideInfo
              })
            }, 1000)

            updateOutsideInfo()

            moveCapture.set('updateOutsideInfo', updateOutsideInfo)

            function sendEventComand (command) {
              return function (e) {
                const eventInfo = {
                  elem_id: getElemId(e.target),
                  outsideInfo: outsideInfo,
                  type: e.type,
                  clientX: e.clientX,
                  clientY: e.clientY,
                  key: e.key,
                  keyCode: e.keyCode,
                  ctrlKey: e.ctrlKey,
                  altKey: e.altKey,
                  shiftKey: e.shiftKey,
                }
                winMessage.send(top, command, eventInfo)
              }
            }

            /**
             * postMessage 不能传递 element 对象
             * 通过 getElemId 方法，用一个 id 标识 element 对象，用于窗口间传递
             * 通过 getElemById 方法，用 id 查找 element 对象
             */
            function getElemId (elem) {
              const nodeType = elem.nodeType

              if (nodeType === 3) {
                const parent = elem.parentNode
                let elem_text_id = $(parent).attr('elem_text_id') || ''
                let child_elem_text_id = ''
                const childIndex = $(parent.childNodes).toArray().findIndex(function (child) { return child === elem })
                const child_elem_text_prefix = 'text_' + childIndex + '_'

                if (!elem_text_id || !~elem_text_id.indexOf(child_elem_text_prefix)) {
                  child_elem_text_id = child_elem_text_prefix + ++frame_elem_id
                  elem_text_id = elem_text_id ? [elem_text_id, child_elem_text_id].join(',') : child_elem_text_id
                  $(parent).attr('elem_text_id', elem_text_id)
                  elem.elem_text_id = child_elem_text_id
                } else {
                  child_elem_text_id = elem_text_id.match(new RegExp(child_elem_text_prefix + '[^,]+'))[0]
                }

                return child_elem_text_id
              } else {
                return $(elem).attr('elem_id') || ($(elem).attr('elem_id', ++frame_elem_id), '' + frame_elem_id)
              }
            }

            function getElemById (elem_id) {
              const isText = elem_id.indexOf('text_') === 0

              if (isText) {
                const parent = $('[elem_text_id*=' + elem_id + ']')[0]
                const childs = parent && $(parent.childNodes).toArray()

                if (!childs) return

                for (let i = 0, len = childs.length; i < len; i++) {
                  if (childs[i].elem_text_id == elem_id) {
                    return childs[i]
                  }
                }
              } else {
                return $('[elem_id=' + elem_id + ']')[0]
              }
            }
          } else {
            $('.psmp-enter').remove()
            workbench = createWorkbench()
            workbench.init(true)
            // TODO dom初始化完成
            voice.play('欢迎访问云南省公共就业服务平台', { forcePlay: true, })

            winMessage.addCommandHandler('mouse', function (reply, eventInfo) {
              const frameInfo = createFrameInfo(reply.target, eventInfo)

              mouseCapture.trigger(['voice_point', 'text_screen', 'voice_continuous'], frameInfo)
            })

            winMessage.addCommandHandler('focus', function (reply, eventInfo) {
              const frameInfo = createFrameInfo(reply.target, eventInfo)

              focusCapture.trigger(['voice_point', 'text_screen'], frameInfo)
            })

            winMessage.addCommandHandler('click', function (reply, eventInfo) {
              const frameInfo = createFrameInfo(reply.target, eventInfo)

              clickCapture.trigger(['voice_continuous'], frameInfo)
            })

            winMessage.addCommandHandler('leave', function (reply, eventInfo) {
              const frameInfo = createFrameInfo(reply.target, eventInfo)

              leaveCapture.trigger(['voice_continuous'], frameInfo)
            })

            winMessage.addCommandHandler('move', function (reply, eventInfo) {
              const clientX = eventInfo.clientX + (eventInfo.clientX - getBodyAttribute('clientWidth') / 2) * (cssScaleFactor - 1)
              const clientY = eventInfo.clientY * cssScaleFactor
              const frameTop = eventInfo.outsideInfo.top
              const frameInfo = createFrameInfo(reply.target, {
                elem_id: eventInfo.elem_id,
                clientX: clientX,
                clientY: clientY + frameTop * pageZoom - getBodyAttribute('scrollTop'),
              })

              moveCapture.trigger(['cross_line'], frameInfo)
            })

            winMessage.addCommandHandler('keyup', function (reply, eventInfo) {
              const frameInfo = createFrameInfo(reply.target, eventInfo)

              keyupCapture.trigger(['shortcut'], frameInfo)
            })

            winMessage.addCommandHandler('scrollTo', function (reply, scrollYOffset) {
              window.scrollTo(0, scrollYOffset)
            })

            winMessage.addCommandHandler('playSource', function (reply, source, config) {
              config || (config = {})

              psmpTool.voice.playSource(source, {
                immediate: config.immediate,
                forcePlay: config.forcePlay,
                ondone: reply,
              })
            })

            winMessage.addCommandHandler('play', function (reply, text, config) {
              config || (config = {})

              psmpTool.voice.play(text, {
                immediate: config.immediate,
                forcePlay: config.forcePlay,
                ondone: reply,
              })
            })
          }
        }())

        function createWorkbench () {
          function Workbench () {
            this.shortcut = new Classes.Shortcut(keyupCapture)
            this.config = new Classes.Config()
          }

          Workbench.prototype = {
            constructor: Workbench,
            init: function () {
              // TODO 样式及html代码
              const psmp_css = '<style id="psmp_css_main" class="psmp_css">.psmp_elem{user-select: none;}#psmp_view_main{width:100%;height:90px;margin:0;padding:0;}.psmp_bg *{margin:0;padding:0;list-style:none;line-height:2;outline:none}.psmp_bg img{border:0;vertical-align:bottom}.psmp_bg a{text-decoration:none}.psmp_bg{width:100%;height: 90px;background:#043b64;position:fixed;left:0;top:0;z-index:1002;white-space: nowrap;}.psmp_nav{max-width:878px;margin:0 auto;}.psmp_nav .tit{width:112px;height:100%;background:url("psmp_assist/psmp/images/line-1.png") no-repeat right bottom;background-size: 4px;display:inline-block;float:left}.psmp_nav .tit ul{margin:66px 0 0 10px;padding:4px;background:#248ff9;display:inline-block;border-radius:5px}.psmp_nav .tit span{padding:2px 5px;font-size:11pt;line-height:1.25;color:#fff;display:inline-block;border:1px solid #fff;border-radius:3px}@media screen and (max-width:944px){    .psmp_nav{text-align:center}    .psmp_nav .tit{display:none}}.psmp_nav .lis img{width:100%;display:inline-block;float:left;}.psmp_nav .lis > i{display:inline-block;float:left}.psmp_nav .lis > ul .line{width:8px;height:50px;display: inline-block;background: transparent!important;}.psmp_nav .lis > ul li{width:54px;height:77px;margin:10px 0 0 6px;text-align:center;display:inline-block;vertical-align: top;}.psmp_nav .lis > ul li .psmp_btn{cursor:pointer;width:100%;font-size:9pt;color:#fff;display:inline-block;white-space: normal;}.psmp_nav .lis > ul li .psmp_btn:hover{color:#fff}.psmp_nav .lis > ul li ul{width:117px;margin-top:60px;margin-left: -54px;background:#fff;border-radius:3px;box-shadow:1px 1px 4px #666;position:relative;display:none;vertical-align: top;white-space: normal;}.psmp_nav .lis > ul li ul hr{height:0px;margin:0 7px;background:#eee;border-top:0;border-left:0}.psmp_nav .lis > ul li ul .myArrow1{width:14px;height:6px;margin:-4px 0 3px 42px;display:block;position:relative;}.psmp_nav .lis .drop_list li{width: calc(100% - 7px);height: auto;margin: 0 3px;padding: 4px 0;}.psmp_nav .lis > ul li li .psmp_btn{font-weight:bold;color:#333;white-space: nowrap;}.psmp_nav .lis > ul li li .psmp_btn:hover{color:#0066B3}.psmp_nav .lis > ul li:hover .drop_list{display:inline-block;}.psmp_nav .lis .drop_list li.active{color:#0066B3;background:url("psmp_assist/psmp/images/active1.png") no-repeat 7px 10px}.psmp_nav .lis .drop_list li.active .psmp_btn{color:#0066B3;}.psmp_nav .lis > ul li ul.programme{width:140px}.psmp_nav .lis > ul li ul.shubiao{width:62px;text-align:right}.psmp_nav .lis > ul li ul.shubiao li img{width:30px;display:inline;float:right}.psmp_nav .lis > ul li ul.shubiao hr{display:none}.psmp_nav .lis .drop_list.shubiao li.active{background-position:7px 12px}.psmp_nav .lis > ul li ul.highContrast{width:94px;border:0;outline:0}.psmp_nav .lis > ul li ul.highContrast span{padding:0 3px;display:inline-block;float:right}#psmp_list_highContrast_1 span{background:#fff;color:#000;border:1px solid #ccc}#psmp_list_highContrast_2 span{background:#00f;color:#ff0;border:1px solid #00f}#psmp_list_highContrast_3 span{background:#ff0;color:#000;border:1px solid #ff0}#psmp_list_highContrast_4 span{background:#000;color:#ff0;border:1px solid #000}#psmp_view_reader{width:100%;height:83px;margin:0;padding:0;}.psmp_spkoly_bg *{margin:0;padding:0;list-style:none;line-height:2;outline:none}.psmp_spkoly_bg img{border:0;vertical-align:bottom}.psmp_spkoly_bg .psmp_btn{cursor:pointer;text-decoration:none;display:inline-block}.psmp_spkoly_bg{width:100%;background:#043b64;position:fixed;left:0;top:0;z-index:1004;}.psmp_spkoly{height:73px;margin:0 auto;padding-top:10px;text-align: center;}.psmp_spkoly .psmp_spkoly_lis{text-align:center;display: inline-block;}.psmp_spkoly .psmp_spkoly_lis .psmp_btn{width:98px;font-size:10pt;color:#fff;line-height:1.3;text-align:left}.psmp_spkoly .psmp_spkoly_lis b{font-size:14pt;color:#fff;display:inline-block}.psmp_spkoly .psmp_spkoly_lis span{color:#ff9c00}.psmp_spkoly .psmp_spkoly_nav{display: inline-block;vertical-align: top;margin-top: 7px;}.psmp_spkoly .psmp_spkoly_nav .psmp_btn{margin-left:7px;border-radius:3px}#psmp_view_bigtext{width:100%;height:125px;margin: 0;padding: 0;}.TextScreen{width:100%;height:125px;background:#043b64;color:#fff;position:fixed;left:0;bottom:0;z-index:1003;}.TextScreen *{margin:0;padding:0;list-style:none}.TextScreen a{color:#fff;text-decoration:none}.TextScreen .BTnav{float:right}.TextScreen .BTnav a{text-align:center;display:inline-block}.TextScreen .BTnav .nav{width:73px;float:right}.TextScreen .BTnav .nav a{width:62px;margin:5px 3px 0 0;font-size:12pt;border:1px solid #fff;border-radius:3px;}.TextScreen .BTnav .nav #psmp_BT_s,.TextScreen .BTnav .nav #psmp_BT_t{width:28px}.TextScreen .BTnav .highContrast{width:77px;float:left}.TextScreen .BTnav .highContrast a{width:73px;margin:3px 3px 0 0;font-size:10pt;line-height:1.5;border-radius:0 3px 3px 0}.TextScreen .BTnav .highContrast #bigtext_hic_t1{background:#fff;color:#000}.TextScreen .BTnav .highContrast #bigtext_hic_t2{background:#00f;color:#ff0}.TextScreen .BTnav .highContrast #bigtext_hic_t3{background:#ff0;color:#000}.TextScreen .BTnav .highContrast #bigtext_hic_t4{background:#000;color:#ff0}.TextScreen .BTnav .highContrast #bigtext_hic_t5{border-right:0px solid #fff}.TextScreen i.icon{width:42px;height:42px;margin-bottom:-26px;background:url("psmp_assist/psmp/images/quit1.png") no-repeat;position:relative;display:inline-block;cursor:pointer;float:right}.TextScreen .BTcont{height:118px;margin-top:3px;padding:0 7px;overflow-y:auto;scrollbar-face-color:#61def5;scrollbar-highlight-color:#61def5;scrollbar-track-color:#085187;}.TextScreen .BTcont::-webkit-scrollbar{width:7px;height:0px}.TextScreen .BTcont::-webkit-scrollbar-thumb{border-radius:7px;box-shadow:inset 0 0 3px rgba(0,0,0,0.2);background:#61def5;}.TextScreen .BTcont::-webkit-scrollbar-track{border-radius:7px;box-shadow:inset 0 0 3px rgba(0,0,0,0.2);background:#085187;}.TextScreen .BTcont h1{color:#fff;font-size:29pt;font-weight:bold;line-height:1.4;text-align:center}#slideLateral{width:100%;height:3px;}#slideLongitudinal{width:3px;height:100%;}#slideLateral, #slideLongitudinal{position:fixed;background-color:#ff0000;overflow:hidden;z-index:999999999;left:0;top:0;}.psmp_active{background:#000;color:#fff}.icon{font-style:normal;display:inline-block}.psmp_nav .icon,.psmp_spkoly .icon{width:55px!important;height:50px!important;background-size: 38px;border-radius:3px;position: static;}.icon.back{background: url(psmp_assist/psmp/images/icons/back.png) no-repeat;background-size: contain;}.icon.back:hover{background: url(psmp_assist/psmp/images/icons/back_hover.png) no-repeat;background-size: contain;}.icon.bigtextOff{background: url(psmp_assist/psmp/images/icons/bigtextOff.png) no-repeat;background-size: contain;}.icon.bigtextOff:hover{background: url(psmp_assist/psmp/images/icons/bigtextOff_hover.png) no-repeat;background-size: contain;}.icon.home{background: url(psmp_assist/psmp/images/icons/home.png) no-repeat;background-size: contain;}.icon.home:hover{background: url(psmp_assist/psmp/images/icons/home_hover.png) no-repeat;background-size: contain;}.icon.guides{background: url(psmp_assist/psmp/images/icons/guides.png) no-repeat;background-size: contain;}.icon.guides:hover{background: url(psmp_assist/psmp/images/icons/guides_hover.png) no-repeat;background-size: contain;}.icon.help{background: url(psmp_assist/psmp/images/icons/help.png) no-repeat;background-size: contain;}.icon.help:hover{background: url(psmp_assist/psmp/images/icons/help_hover.png) no-repeat;background-size: contain;}.icon.highContrast{background: url(psmp_assist/psmp/images/icons/highContrast.png) no-repeat;background-size: contain;}.icon.highContrast:hover{background: url(psmp_assist/psmp/images/icons/highContrast_hover.png) no-repeat;background-size: contain;}.icon.mouseArrow{background: url(psmp_assist/psmp/images/icons/mouseArrow.png) no-repeat;background-size: contain;}.icon.mouseArrow:hover{background: url(psmp_assist/psmp/images/icons/mouseArrow_hover.png) no-repeat;background-size: contain;}.icon.pageZoomIn{background: url(psmp_assist/psmp/images/icons/pageZoomIn.png) no-repeat;background-size: contain;}.icon.pageZoomIn:hover{background: url(psmp_assist/psmp/images/icons/pageZoomIn_hover.png) no-repeat;background-size: contain;}.icon.pageZoomDe{background: url(psmp_assist/psmp/images/icons/pageZoomDe.png) no-repeat;background-size: contain;}.icon.pageZoomDe:hover{background: url(psmp_assist/psmp/images/icons/pageZoomDe_hover.png) no-repeat;background-size: contain;}.icon.pointerOff{background: url(psmp_assist/psmp/images/icons/pointerOff.png) no-repeat;background-size: contain;}.icon.pointerOff:hover{background: url(psmp_assist/psmp/images/icons/pointerOff_hover.png) no-repeat;background-size: contain;}.icon.pointerOn{background: url(psmp_assist/psmp/images/icons/pointerOn.png) no-repeat;background-size: contain;}.icon.pointerOn:hover{background: url(psmp_assist/psmp/images/icons/pointerOn_hover.png) no-repeat;background-size: contain;}.icon.preview{background: url(psmp_assist/psmp/images/icons/preview.png) no-repeat;background-size: contain;}.icon.preview:hover{background: url(psmp_assist/psmp/images/icons/preview_hover.png) no-repeat;background-size: contain;}.icon.programme{background: url(psmp_assist/psmp/images/icons/programme.png) no-repeat;background-size: contain;}.icon.programme:hover{background: url(psmp_assist/psmp/images/icons/programme_hover.png) no-repeat;background-size: contain;}.icon.programme_1{background: url(psmp_assist/psmp/images/icons/programme_1.png) no-repeat;background-size: contain;}.icon.programme_1:hover{background: url(psmp_assist/psmp/images/icons/programme_1_hover.png) no-repeat;background-size: contain;}.icon.programme_2{background: url(psmp_assist/psmp/images/icons/programme_2.png) no-repeat;background-size: contain;}.icon.programme_2:hover{background: url(psmp_assist/psmp/images/icons/programme_2_hover.png) no-repeat;background-size: contain;}.icon.quit{background: url(psmp_assist/psmp/images/icons/quit.png) no-repeat;background-size: contain;}.icon.quit:hover{background: url(psmp_assist/psmp/images/icons/quit_hover.png) no-repeat;background-size: contain;}.icon.services{background: url(psmp_assist/psmp/images/icons/services.png) no-repeat;background-size: contain;}.icon.services:hover{background: url(psmp_assist/psmp/images/icons/services_hover.png) no-repeat;background-size: contain;}.icon.soundOff{background: url(psmp_assist/psmp/images/icons/soundOff.png) no-repeat;background-size: contain;}.icon.soundOff:hover{background: url(psmp_assist/psmp/images/icons/soundOff_hover.png) no-repeat;background-size: contain;}.icon.soundOn{background: url(psmp_assist/psmp/images/icons/soundOn.png) no-repeat;background-size: contain;}.icon.soundOn:hover{background: url(psmp_assist/psmp/images/icons/soundOn_hover.png) no-repeat;background-size: contain;}.icon.speaking{background: url(psmp_assist/psmp/images/icons/speaking.png) no-repeat;background-size: contain;}.icon.speaking:hover{background: url(psmp_assist/psmp/images/icons/speaking_hover.png) no-repeat;background-size: contain;}.icon.speakingOff{background: url(psmp_assist/psmp/images/icons/speakingOff.png) no-repeat;background-size: contain;}.icon.speakingOff:hover{background: url(psmp_assist/psmp/images/icons/speakingOff_hover.png) no-repeat;background-size: contain;}.icon.speakonly{background: url(psmp_assist/psmp/images/icons/speakonly.png) no-repeat;background-size: contain;}.icon.speakonly:hover{background: url(psmp_assist/psmp/images/icons/speakonly_hover.png) no-repeat;background-size: contain;}.icon.speedDown{background: url(psmp_assist/psmp/images/icons/speedDown.png) no-repeat;background-size: contain;}.icon.speedDown:hover{background: url(psmp_assist/psmp/images/icons/speedDown_hover.png) no-repeat;background-size: contain;}.icon.speedUp{background: url(psmp_assist/psmp/images/icons/speedUp.png) no-repeat;background-size: contain;}.icon.speedUp:hover{background: url(psmp_assist/psmp/images/icons/speedUp_hover.png) no-repeat;background-size: contain;}.icon.textonly{background: url(psmp_assist/psmp/images/icons/textonly.png) no-repeat;background-size: contain;}.icon.textonly:hover{background: url(psmp_assist/psmp/images/icons/textonly_hover.png) no-repeat;background-size: contain;}</style>'
              const $psmp_html = $('<div id="psmp_view_main" class="psmp_elem psmp_zoom"><div class="psmp_bg"><div class="psmp_nav" id="psmpNav"><div class="lis"><ul><li><span class="psmp_btn"><i class="icon soundOn"></i>声音开关</span></li><li><span class="psmp_btn"><i class="icon pointerOn"></i>语音指读</span></li><li><span class="psmp_btn"><i class="icon speaking"></i>语音连读</span></li><i class="line"></i><li><span class="psmp_btn"><i class="icon bigtextOff"></i>大字幕</span></li><li><span class="psmp_btn"><i class="icon pageZoomIn"></i>页面增大</span></li><li><span class="psmp_btn"><i class="icon pageZoomDe"></i>页面缩小</span></li><i class="line"></i><li><span class="psmp_btn"><i class="icon highContrast"></i>配色</span><ul class="drop_list"><i class="myArrow1"><img src="psmp_assist/psmp/images/arrows-1.png"></i><li><span class="psmp_btn" theme="#fff_#000">白底黑字</span></li><hr><li><span class="psmp_btn" theme="#00f_#ff0">蓝底黄字</span></li><hr><li><span class="psmp_btn" theme="#ff0_#000">黄底黑字</span></li><hr><li><span class="psmp_btn" theme="#000_#ff0">黑底黄字</span></li><hr><li class="active"><span class="psmp_btn">原始配色</span></li></ul></li><li><span class="psmp_btn"><i class="icon speedUp"></i>语音加速</span></li><li><span class="psmp_btn"><i class="icon speedDown"></i>语音减速</span></li><i class="line"></i><li><span class="psmp_btn"><i class="icon mouseArrow"></i>鼠标样式</span><ul class="drop_list shubiao" style="width: 90px;text-align: right;"><i class="myArrow1"><img src="psmp_assist/psmp/images/arrows-1.png"></i><li><span class="psmp_btn" cursor="shubiao_bai"><img src="psmp_assist/psmp/images/shubiao_bai.png"></span></li><hr><li><span class="psmp_btn" cursor="shubiao_huang"><img src="psmp_assist/psmp/images/shubiao_huang.png"></span></li><hr><li><span class="psmp_btn" cursor="shubiao_lan"><img src="psmp_assist/psmp/images/shubiao_lan.png"></span></li><hr><li><span class="psmp_btn" cursor="shubiao_lv"><img src="psmp_assist/psmp/images/shubiao_lv.png"></span></li><hr><li class="active"><span class="psmp_btn"><img src="psmp_assist/psmp/images/shubiao_ys.png"></span></li></ul></li><li><span class="psmp_btn"><i class="icon guides"></i>十字线</span></li><li><span class="psmp_btn"><i class="icon help"></i>帮助</span></li><i class="line"></i><li><span class="psmp_btn"><i class="icon speakonly"></i>读屏专用</span></li><li><span class="psmp_btn"><i class="icon quit"></i>退出服务</span></li></ul></div></div></div></div>')
              // $reader_html = $('<div id="psmp_view_reader" class="psmp_elem psmp_zoom"><div class="psmp_spkoly_bg" id="SpeakerOnly"><div class="psmp_spkoly"><div class="psmp_spkoly_lis"><span class="psmp_btn" href="javascript:void(0)" id="nav_nav"><b>导航区</b><span>(6)</span><br>ALT+1</span><span class="psmp_btn" href="javascript:void(0)" id="nav_window"><b>视窗区</b><span>(6)</span><br>ALT+2</span><span class="psmp_btn" href="javascript:void(0)" id="nav_interactive"><b>交互区</b><span>(2)</span><br>ALT+3</span><span class="psmp_btn" href="javascript:void(0)" id="nav_list"><b>列表区</b><span>(2)</span><br>ALT+4</span><span class="psmp_btn" href="javascript:void(0)" id="nav_article"><b>正文区</b><span>(2)</span><br>ALT+5</span><span class="psmp_btn" href="javascript:void(0)" id="nav_servis"><b>服务区</b><span>(3)</span><br>ALT+6</span></div><div class="psmp_spkoly_nav"><span class="psmp_btn" name="声音开关" href="javascript:void(0)" title="当前声音已关闭，开启声音Shift+A" id="psmp_spk_soundOnOff"><i class="icon soundOn"></i></span><span class="psmp_btn" name="帮助" href="psmp_assist/psmp/help.html" title="开启操作说明Shift+问号键"><i class="icon help"></i></span><span class="psmp_btn" name="大字幕" href="javascript:void(0)" title="当前大字幕已开启，关闭大字幕Shift+K" id="psmp_spk_BigText"><i class="icon bigtextOff"></i></span><span class="psmp_btn" name="老人服务" href="javascript:void(0)" title="老人服务：快捷键Shift+1，适用于视力较弱人群" id="psmp_spk_speakOnly"><i class="icon speakonly"></i></span><span class="psmp_btn" name="退出服务" href="javascript:void(0)" title="退出服务Shift+Esc" id="psmp_spk_exit"><i class="icon quit"></i></span></div></div></div></div></div>')
              const $reader_html = $('<div id="psmp_view_reader" class="psmp_elem psmp_zoom"><div class="psmp_spkoly_bg" id="SpeakerOnly"><div class="psmp_spkoly"><div class="psmp_spkoly_lis"><span style="color: #fff;font-size: 20px;font-weight: bold;;line-height: 60px;">适老模式已开启</span></div><div class="psmp_spkoly_nav"><span class="psmp_btn" name="声音开关" href="javascript:void(0)" title="当前声音已关闭，开启声音Shift+A" id="psmp_spk_soundOnOff"><i class="icon soundOn"></i></span><span class="psmp_btn" name="帮助" href="psmp_assist/psmp/help.html" title="开启操作说明Shift+问号键"><i class="icon help"></i></span><span class="psmp_btn" name="大字幕" href="javascript:void(0)" title="当前大字幕已开启，关闭大字幕Shift+K" id="psmp_spk_BigText"><i class="icon bigtextOff"></i></span><span class="psmp_btn" name="老人服务" href="javascript:void(0)" title="老人服务：快捷键Shift+1，适用于视力较弱人群" id="psmp_spk_speakOnly"><i class="icon speakonly"></i></span><span class="psmp_btn" name="退出服务" href="javascript:void(0)" title="退出服务Shift+Esc" id="psmp_spk_exit"><i class="icon quit"></i></span></div></div></div></div></div>')
              const $audio_html = $('<div id="psmp_audio_main"><audio id="tts_autio_id" autoplay="autoplay" src=""><source id="".concat(namespace, "-audio-source") src="" type="audio/mpeg"><embed id="".concat(namespace, "-audio-embed" height="0" width="0" src=""></audio></div>')
              $('body').prepend($audio_html)
              $('body').prepend($reader_html)
              $('body').prepend($psmp_html)
              $('body').prepend(psmp_css)

              this.$elem = $psmp_html
              this.$reader_elem = $reader_html
              this.$audio_elem = $audio_html
              this.$reader_elem.hide()
              this.$audio_elem.hide()

              $('.psmp_btn', this.$elem).each(function (i, elem) {
                const name = $(elem).text()
                $(elem).find('.icon').attr('aria-label', name)
              })

              $('.psmp_btn', this.$reader_elem).each(function (i, elem) {
                const name = $(elem).attr('name')
                $(elem).find('.icon').attr('aria-label', name)
              })

              this.setButton()
              this.setShortCut()
              // 是否通过老年模式打开
              if (model) {
                this.setZoomMax()
              }
            },
            setZoomMax: function () {
              psmpTool.zoom.increase(1.3)
              $('iframe').toArray().forEach(function (frame) {
                winMessage.send(frame.contentWindow, 'zoom', cssZoomFactor)
              })
              workbench.keepSize()
            },
            setShortCut: function () {
              const shortcut = this.shortcut

              shortcut.set('ctrl+`', trigger('click', Button('退出服务')))
              shortcut.set('alt+shift+?', trigger('click', Button('帮助')))
              shortcut.set('alt+shift+l', trigger('click', Button('声音开关')))
              shortcut.set('alt+shift+-', trigger('click', Button('语音指读')))
              shortcut.set('alt+shift+=', trigger('click', Button('语音连读')))
              shortcut.set('alt+shift+ArrowRight', trigger('click', Button('语音加速'))) // 右箭头
              shortcut.set('alt+shift+ArrowLeft', trigger('click', Button('语音减速'))) // 左箭头
              shortcut.set('alt+shift+[', trigger('click', Button('大字幕')))
              shortcut.set('alt+shift+7', trigger('click', Button('页面增大')))
              shortcut.set('alt+shift+8', trigger('click', Button('页面缩小')))
              shortcut.set('alt+shift+2', dropListTrigger('click', Button('鼠标样式')))
              shortcut.set('alt+shift+5', dropListTrigger('click', Button('配色')))
              shortcut.set('alt+shift+6', trigger('click', Button('十字线')))
              shortcut.set('alt+shift+z', trigger('click', Button('读屏专用')))
              shortcut.set('shift+1', trigger('click', Button('老人服务')))

              function trigger (eventType, $btn) {
                return function () {
                  $btn.eq(0).trigger(eventType)
                }
              }

              function dropListTrigger (eventType, $btn) {
                const $dropList = $btn.next('.drop_list')
                const $btns = $dropList.find('.psmp_btn').parent()
                const count = $btns.length
                return function () {
                  const nextIndex = ($btns.index($dropList.find('.active')) + 1) % count
                  $btns.eq(nextIndex).find('.psmp_btn').trigger(eventType)
                }
              }
            },
            setButton: function () {
              __SupportKeyboard__ = false

              const workbench = this

              Button('声音开关')
                .$on(function () {
                  setIcon('声音开关', 'soundOn')
                  this.attr('title', '当前声音已开启，关闭声音Alt+Shift+L')

                  psmpTool.voice.on()
                  workbench.config.set('sound-off', false)
                })
                .$off(function () {
                  setIcon('声音开关', 'soundOff')
                  this.attr('title', '当前声音已关闭，开启声音Alt+Shift+L')

                  psmpTool.voice.off()
                  workbench.config.set('sound-off', 1)
                })

              Button('语音指读')
                .$on(function () {
                  Button('语音连读').$off()
                  setIcon('语音指读', 'pointerOn')

                  const play = debounce(psmpTool.voice.play.bind(psmpTool.voice), 200)
                  let lastText = ''; let sid

                  function voicePointHandler (e) {
                    extractText(e).then(function (result) {
                      let text = result.content

                      if (~'focusin'.indexOf(e.type) && result.focus_label) {
                        text = join(text, result.focus_label)
                      }

                      if (lastText === text) return

                      if (sid) clearTimeout(sid)

                      lastText = text
                      sid = setTimeout(function () {
                        lastText = ''
                      }, 300)

                      play(text)
                    })
                  }

                  mouseCapture.set('voice_point', voicePointHandler)

                  focusCapture.set('voice_point', function (e) {
                    setTimeout(voicePointHandler, 200, e)
                  })
                })
                .$off(function () {
                  setIcon('语音指读', 'pointerOff')

                  mouseCapture.remove('voice_point')
                  focusCapture.remove('voice_point')
                  psmpTool.voice.pause()
                })

              Button('语音连读')
                .$on(function () {
                  Button('语音指读').$off()
                  setIcon('语音连读', 'speaking')

                  let isPlaying = false
                  const playText = psmpTool.voice.play.bind(psmpTool.voice)
                  let sid; let lastText

                  function voiceContinuousHandler (e) {
                    if (isPlaying) return

                    if (sid) clearTimeout(sid)

                    sid = setTimeout(function () {
                      sid = void 0
                      isPlaying = true
                      continuousPlay(e)
                    }, 2000)

                    function continuousPlay (elem) {
                      if (!elem) {
                        lastText = void 0
                        return isPlaying = false
                      }

                      extractText(elem)
                        .then(function (result) {
                          let text = result.content

                          if (lastText === text) text = ''
                          text && markPlayingElem(elem, true)
                          return text
                        })
                        .then(playText)
                        .then(function (text) {
                          text && (lastText = text, markPlayingElem(elem))

                          if (isPlaying) return elem
                          throw '中断连读'
                        })
                        .then(getNextElem)
                        .then(continuousPlay)
                        .catch(function (err) {
                          lastText = void 0
                          isPlaying = false
                        })
                    }
                  }

                  function clearContinuousPlay () {
                    if (sid) clearTimeout(sid)
                    sid = void 0
                  }

                  function stopContinuousPlay () {
                    clearContinuousPlay()
                    isPlaying = false
                    psmpTool.voice.pause()
                  }

                  this.stopContinuousPlay = stopContinuousPlay

                  psmpTool.voice.play('开启连读模式。请将鼠标移到需要阅读文字上，2秒后开始阅读。在阅读链接时敲击回车键即可进入链接指向的页面')
                    .catch(function () {
                      setIcon('语音连读', 'speakingOff')
                    })
                  setTimeout(function () {
                    mouseCapture.set('voice_continuous', voiceContinuousHandler)
                    clickCapture.set('voice_continuous', stopContinuousPlay)
                    leaveCapture.set('voice_continuous', clearContinuousPlay)
                  }, 500)
                })
                .$off(function () {
                  setIcon('语音连读', 'speakingOff')

                  this.stopContinuousPlay && this.stopContinuousPlay()
                  mouseCapture.remove('voice_continuous')
                  clickCapture.remove('voice_continuous')
                  leaveCapture.remove('voice_continuous')
                })

              Button('大字幕')
                .$on(function () {
                  psmpTool.textScreen.show()

                  function textScreenHandler (e) {
                    extractText(e).then(function (result) {
                      const text = result.content
                      text && psmpTool.textScreen.setText(text)
                    })
                  }

                  mouseCapture.set('text_screen', textScreenHandler)
                  focusCapture.set('text_screen', textScreenHandler)

                  this.attr('title', '当前大字幕已开启，关闭大字幕Alt+Shift+[')
                })
                .$off(function () {
                  psmpTool.textScreen.hide()

                  mouseCapture.remove('text_screen')
                  focusCapture.remove('text_screen')

                  this.attr('title', '当前大字屏已关闭，开启打字屏Alt+Shift+[')
                })

              Button('页面增大').click(function () {
                psmpTool.zoom.increase()
                $('iframe').toArray().forEach(function (frame) {
                  winMessage.send(frame.contentWindow, 'zoom', cssZoomFactor)
                })

                workbench.keepSize()
              })

              Button('页面缩小').click(function () {
                psmpTool.zoom.decrease()
                $('iframe').toArray().forEach(function (frame) {
                  winMessage.send(frame.contentWindow, 'zoom', cssZoomFactor)
                })

                workbench.keepSize()
              })

              Button('读屏专用').click(function () {
                workbench.$reader_elem.show()
                workbench.$elem.hide()
                workbench.setReaderArea()
                workbench.config.set('reader-mode-on', true)
              })

              Button('帮助').click(go('psmp_assist/psmp/help.html', '_blank'))

              Button('语音加速').click(function () {
                psmpTool.voice.play('语音加速')
                psmpTool.voice.speedUp()
              })

              Button('语音减速').click(function () {
                psmpTool.voice.play('语音减速')
                psmpTool.voice.speedDown()
              })

              Button('鼠标样式').next('.drop_list').click(function (e) {
                const $target = $(e.target)
                const cursor = $target.closest('[cursor]').attr('cursor')

                psmpTool.cursor.set(cursor)
                workbench.config.set('cursor-option', cursor || false)
              })

              Button('十字线')
                .$on(function () {
                  psmpTool.cross_line.show()
                  workbench.config.set('cross-line-on', '1')
                  moveCapture.set('cross_line', function (e) {
                    psmpTool.cross_line.draw(e.clientX, e.clientY)
                  })
                })
                .$off(function () {
                  psmpTool.cross_line.hide()
                  workbench.config.set('cross-line-on', false)
                  moveCapture.remove('cross_line')
                })

              Button('配色').next('.drop_list').click(function (e) {
                const $target = $(e.target)
                const theme = $target.closest('[theme]').attr('theme')
                const tuple = (theme || '_').split('_')
                const background = tuple[0]
                const color = tuple[1]

                psmpTool.theme.set(background, color)
                workbench.config.set('theme-option', theme || false)
              })

              Button('老人服务').click(function () {
                workbench.$elem.show()
                workbench.$reader_elem.hide()
                workbench.config.set('reader-mode-on', false)
              })
              // TODO 退出操作
              Button('退出服务').click(function () {
                removeCookie('psmp')
                workbench.config.set('cross-line-on', false)
                workbench.config.set('cursor-option', false)
                // 隐藏无障碍工具栏
                // $('#psmp_css_main').hide()
                // $('#psmp_view_main').hide()
                // $('#psmp_view_reader').hide()
                // $('#psmp_audio_main').hide()
                // 重新加载首页
                ~location.search.indexOf('psmp') ? (location.search = location.search.replace(/psmp=[^&]*/, '')) : location.reload()
              })

              $('.drop_list:not(.active)').click(function (e) {
                const $target = $(e.target)
                $target.closest('li').addClass('active').siblings().removeClass('active')
              })

              __SupportKeyboard__ = true

              if (isBoolean(showReader)) {
                workbench.config.set('reader-mode-on', showReader)
                if (showReader) {
                  workbench.config.set('sound-off', true)
                }
              }

              const config = workbench.config.get()

              { // 默认配置
                Button('声音开关').$on()
                Button('大字幕').$off()
                Button('语音指读').$on()
                psmpTool.voice.speedReset()
              }

              config['cross-line-on'] && Button('十字线').$on()
              config['reader-mode-on'] && Button('读屏专用').click()
              config['sound-off'] && Button('声音开关').$off()

              const cursor = config['cursor-option']
              cursor && Button('鼠标样式').next('.drop_list').find('[cursor=' + cursor + ']').click()

              const theme = config['theme-option']
              theme && Button('配色').next('.drop_list').find('[theme=' + theme + ']').click()
            },
            keepSize: function () {
              const fix_zoom = 1 / pageZoom

              if (useCssZoom) {
                createStyle('psmp_css_zoom', 'psmp_css', '.psmp_zoom{zoom:' + fix_zoom + '!important;}')
              } else {
                $('.psmp_zoom').toArray().forEach(function (psmp_zoom_elem) {
                  $(psmp_zoom_elem).height($(psmp_zoom_elem.firstChild).height() * fix_zoom)
                })
              }
            },
            setReaderArea: function () {
              if (this.ReaderMode) return

              this.ReaderMode = true

              const areaNames = ['nav', 'window', 'interactive', 'list', 'article', 'servis']
              const areaDescs = []
              let areaTotal = 0
              const areas = {}

              __SupportKeyboard__ = false

              areaNames.forEach(function (areaName) {
                const $area_elem = $('.psmp_area_' + areaName)
                const $area_tab = $('#nav_' + areaName)
                const area_chinese = $area_tab.find('>b').text()
                const area_elem_count = $area_elem.length

                $area_tab.find('>span').text('(' + area_elem_count + ')')

                if (area_elem_count) {
                  areaTotal += area_elem_count
                  areaDescs.push(area_elem_count + '个' + area_chinese)
                  areas[areaName] = $area_elem
                  $area_tab.click(locateOn($area_elem))
                  $area_elem.filter('a:not([href])').attr('tabindex', '0')
                  $area_elem.filter(':not(a,:input,[tabindex])').prepend('<a class="psmp_area_locate_point" tabindex="0"></a>')
                }
              })

              if (!areaTotal) return __SupportKeyboard__ = true

              $('body')
                .prepend('<style id="psmp_css_locate" class="psmp_css">.psmp_area_locate_outline{outline:2px solid red;outline-offset:-1px;}.psmp_area_locate_point{width:1px!important;height:0px!important;float:left!important;}.psmp_area_locate_bg{background-color:rgba(127,127,127,0.4);}</style>')
                .focusin(function (e) {
                  const $focus = $(e.target)
                  if ($focus.hasClass('psmp_area_locate_point')) {
                    $('.psmp_area_locate_bg').removeClass('psmp_area_locate_bg')
                    $focus.parent().addClass('psmp_area_locate_bg')
                  } else {
                    $('.psmp_area_locate_outline').removeClass('psmp_area_locate_outline')
                  }
                })
                .click(function (e) {
                  const $elem = $(e.target)
                  if (!$elem.closest('.psmp_area_locate_outline,.psmp_spkoly_lis').length) {
                    $('.psmp_area_locate_outline').removeClass('psmp_area_locate_outline')
                  }
                })

              __SupportKeyboard__ = true

              const descText = '本页是由' + areaDescs.join('、') + '共计' + areaTotal + '个区域组成，操作帮助请按Alt加问号键'
              const shortcut = this.shortcut

              psmpTool.voice.play(descText)
              psmpTool.textScreen.setText(descText)

              shortcut.set('alt+1', locateOn(areas.nav))
              shortcut.set('alt+2', locateOn(areas.window))
              shortcut.set('alt+3', locateOn(areas.interactive))
              shortcut.set('alt+4', locateOn(areas.list))
              shortcut.set('alt+5', locateOn(areas.article))
              shortcut.set('alt+6', locateOn(areas.servis))

              function locateOn ($area_elem) {
                if (!$area_elem) return

                return function () {
                  const nextIndex = ($area_elem.index($area_elem.filter('.psmp_area_locate')) + 1) % $area_elem.length
                  const $target = (
                    $('.psmp_area_locate').removeClass('psmp_area_locate psmp_area_locate_outline'),
                    $area_elem.eq(nextIndex).addClass('psmp_area_locate psmp_area_locate_outline')
                  )
                  let $focus = $target.find('>.psmp_area_locate_point')

                  scrollToView($target[0])
                  $focus.length || ($focus = $target)
                  $focus.focus()
                }
              }
            },
            height: function () {
              return (!!workbench.config.get('reader-mode-on') ? $('#psmp_view_reader').height() : $('#psmp_view_main').height()) / cssZoomFactor
            },
          }

          return new Workbench()
        }

        function markPlayingElem (elem, shouldMark) {
          if (isFrameInfo(elem)) {
            return void winMessage.send(elem.frame, 'markPlayingElem', elem.elem_id, shouldMark)
          }

          elem = normalizeElem(elem)

          elem.nodeType === 3 && (elem = elem.parentNode)

          if (shouldMark) {
            var style = $(elem).attr('style')
            const cssStyle = elem.innerText
              ? { 'text-decoration': 'underline', }
              : { outline: '2px solid red', 'outline-offset': '-1px', }

            $(elem).css(cssStyle).data('style', style || null)
            scrollToView(elem)

            if ($(elem).is('a[href]:not([href^=javascript]):not([href^=#])')) {
              keyupCapture.set('continuousPlay_enter', function (e) {
                if (e.keyCode !== 13) return
                elem.click()
              })
            }
          } else {
            var style = $(elem).data('style')
            if (style !== void 0) {
              $(elem).attr('style', style)
            }

            keyupCapture.remove('continuousPlay_enter')
          }
        }

        function getNextElem (elem) {
          if (isFrameInfo(elem)) {
            return new Promise(function (resolve) {
              winMessage.send(elem.frame, 'getNextElem', elem.elem_id).waitReply(function (reply, frameInfo) {
                timeout && clearTimeout(timeout)
                timeout = void 0

                if (frameInfo) {
                  /* 返回来自 iframe 窗口的数据 */
                  resolve(setFrame(frameInfo))
                } else if (!isInFrame) {
                  /* 由顶层窗口直接获取 iframe 元素的下一个元素 */
                  resolve(getNextElem(getFrameElem(reply.target)))
                }
              })

              var timeout = setTimeout(function () {
                timeout = void 0
                resolve()
              }, 3000)
            })
          }

          if (!elem) return Promise.resolve()

          elem = normalizeElem(elem)

          let next = elem.nextSibling

          if (!next) {
            /**
             * 查找下一个最近的分支节点
             */
            do {
              elem = elem.parentNode
              next = elem && elem.nextSibling
            } while (elem && !next)

            /**
             * 没有找到分支节点，代表窗口已经没有下一个元素
             */
            if (!next) {
              /**
               * 如果父窗口是 iframe，向父窗口发送命令，由父窗口从当前窗口所属的 iframe 元素继续查找下一个元素
               * 如果当前窗口是 iframe，结束查找，并由顶层窗口从当前窗口所属的 iframe 元素继续查找下一个元素
               * 如果当前窗口不是 iframe，结束查找。
               */
              const isParentInFrame = parent !== top
              return isParentInFrame
                ? getNextElem(createFrameInfo(parent))
                : Promise.resolve()
            }
          }

          /**
           * 查找可用的叶子节点
           */
          while (next && next.nodeType === 1 && !excludeElem(next) && checkVisible(next) && next.childNodes[0]) {
            next = next.childNodes[0]
          }

          return Promise.resolve(checkVisible(next) && (next.nodeType === 3 ? /\S/.test(next.textContent) : next.nodeType === 1))
            .then(function (isReadable) {
              return isReadable
                ? next.nodeName && next.nodeName.toLowerCase() === 'iframe'
                  /* 当找到的是iframe元素，则进入iframe，获取第一个元素 */
                  ? getNextElem(createFrameInfo(next.contentWindow))
                  /* 返回当前窗口的元素 */
                  : next
                : getNextElem(next)
            })

          function excludeElem (elem) {
            return $(elem).is('body > .sidebar,select')
          }
        }

        function extractText (elem) {
          if (isFrameInfo(elem)) {
            return new Promise(function (resolve) {
              winMessage.send(elem.frame, 'extractText', elem.elem_id).waitReply(function (reply, result) {
                resolve(result)
              })
            })
          }

          elem = normalizeElem(elem)

          const nodeType = elem.nodeType
          const isElem = nodeType === 1
          const isText = nodeType === 3
          const isVisible = checkVisible(elem)
          const result = {
            content: '',
          }

          if (!isVisible) {
            return Promise.resolve(result)
          }

          if (isText) {
            elem.textContent.match(/\S/) && (result.content = elem.textContent)
            return Promise.resolve(result)
          }

          if (!isElem) return Promise.resolve(result)

          const nodeName = elem.nodeName.toLowerCase()

          if (nodeName === 'script' || nodeName === 'style') {
            return Promise.resolve(result)
          }

          // var result$ = getSpecialElemInfo(elem)

          // if (result$) {
          //     Object.assign(result, result$)
          // }

          // var focus_label = elem.getAttribute("focus_label")

          // if (focus_label) {
          //     result.focus_label = focus_label
          // }

          let text = elem.getAttribute('aria-label') || nodeName === 'img' && elem.getAttribute('alt') || ''

          if (text) {
            result.content = text
          }

          if (result.content) return Promise.resolve(result)

          const texts = []

          $(elem.childNodes).each(function (i, child) {
            const nodeType = child.nodeType
            let childText

            if (nodeType === 3 && child.textContent.match(/\S/)) {
              childText = child.textContent
            } else if (nodeType === 1) {
              const childName = child.nodeName.toLowerCase()

              if (~['a', 'span', 'p'].indexOf(childName)) {
                childText = child.innerText
              }
            }

            childText && texts.push(childText)
          })

          texts.length && (text = texts.join('\n'))
          result.content = text

          return Promise.resolve(result)
        }

        function checkVisible (elem) {
          const target = elem.nodeType === 3 ? elem.parentNode : elem
          const isVisible = $(target).is(':visible')
          return isVisible
        }

        function getBodyAttribute (attr) {
          return document.documentElement[attr] || document.body[attr]
        }

        function scrollToView (elem) {
          elem.nodeType === 3 && (elem = elem.parentNode)

          const stableDistance = 40
          const belowWorkBenchDistance = 60

          if (isInFrame) {
            winMessage.send(parent, 'frameOutsideInfo').waitReply(function (reply, outsideInfo) {
              const outScrollTop = outsideInfo.scrollTop
              const nowScrollY = getBodyAttribute('scrollTop') + outScrollTop
              const workbench_height = outsideInfo.workbench_height
              const willScrollY = (offset(elem).top + outsideInfo.top - workbench_height - belowWorkBenchDistance) * cssZoomFactor

              if (Math.abs(nowScrollY - willScrollY) > stableDistance) {
                winMessage.send(top, 'scrollTo', willScrollY)
              }
            })
          } else {
            const nowScrollY = getBodyAttribute('scrollTop')
            const workbench_height = workbench.height()
            const willScrollY = (offset(elem).top - workbench_height - belowWorkBenchDistance) * cssZoomFactor

            if (Math.abs(nowScrollY - willScrollY) > stableDistance) {
              window.scrollTo(0, willScrollY)
            }
          }
        }

        function normalizeElem (elem) {
          elem = elem.nodeType ? elem : elem.target
          elem === document && (elem = elem.documentElement)
          return elem
        }

        function genFrameInfo (info) {
          if (!frame_id) throw '不在 iframe 窗口'
          if (!isPlainObject(info)) throw 'genFrameInfo 参数不是对象'

          return Object.assign({
            frame_id: frame_id,
          }, info)
        }

        function setFrame (frameInfo) {
          if (!isFrameInfo(frameInfo)) throw 'setFrame 参数不是 frameInfo'

          if (!isInFrame) {
            frameInfo.frame = frameWindows[frameInfo.frame_id]
          }

          return frameInfo
        }

        function createFrameInfo (frame, info) {
          return Object.assign({
            frame: frame,
          }, info)
        }

        function isFrameInfo (frameInfo) {
          return isPlainObject(frameInfo) && (frameInfo.frame || frameInfo.frame_id)
        }

        function processAriaLabel () {
          var handlers = {
            'a[href]:not([href^=javascript]):not([href^=#])': function ($elems) {
              return {
                label: function (elem, genLabel) {
                  return genLabel('链接', $(elem).text())
                },
              }
            },
            'a[keyboard_fires*=click]:not([href])': function ($elems) {
              return {
                label: function (elem, genLabel) {
                  return genLabel('按钮', $(elem).text())
                },
              }
            },
            '.select_list': function ($elems) {
              return {
                label: function (elem, genLabel) {
                  return genLabel('下拉框', $(elem).find('.select_value').text())
                },
                hooks: {
                  click: function (elem, genLabel) {
                    useHandler($(elem).find('.select_ul li'), handlers['.select_ul li'])
                  },
                  onTAB: function (elem, genLabel) {
                    return genLabel('下拉框', $(elem).find('.select_value').text(), '按回车键打开')
                  },
                  mouseover: function (elem, genLabel) {
                    return genLabel('下拉框', $(elem).find('.select_value').text())
                  },
                },
              }
            },
            '.select_ul li': function ($elems) {
              return {
                label: function (elem, genLabel) {
                  return genLabel('下拉框选项', $(elem).text())
                },
              }
            },
            '#header_box .chengshixuanze .select-city': function ($elems) {
              return {
                label: function (elem, genLabel) {
                  return '地区选择器 ' + $(elem).text()
                },
                hooks: {
                  onTAB: function (elem, genLabel) {
                    return genLabel('地区选择器', $(elem).text(), '按回车键打开')
                  },
                  mouseover: function (elem, genLabel) {
                    return genLabel('地区选择器', $(elem).text())
                  },
                },
              }
            },
            '[onclick^=laydate]': function ($elems) {
              return {
                label: '日期选择器',
              }
            },
            ':text': function ($elems) {
              return {
                label: function (elem, genLabel) {
                  return genLabel('输入框', $(elem).val() || $(elem).attr('placeholder') || '')
                },
              }
            },
            ':password': function ($elems) {
              return {
                label: '密码输入框',
              }
            },
            ':checkbox': function ($elems) {
              return {
                label: function (elem, genLabel) {
                  return genLabel('复选框', $(elem).hasClass('r_on') && '已选中')
                },
                hooks: {
                  click: function (elem, genLabel) {
                    return genLabel('复选框', $(elem).hasClass('r_on') && '已选中')
                  },
                  // onTAB: function () {
                  //     return genLabel("复选框", $elem.hasClass("r_on") && "已选中")
                  // },
                  mouseover: function (elem, genLabel) {
                    return genLabel('复选框', $(elem).hasClass('r_on') && '已选中')
                  },
                },
              }
            },
            ':radio:not(.label_radio :radio)': function ($elems) {
              return {
                label: function (elem, genLabel) {
                  return genLabel('单选框', $(elem).prop('checked') && '已选中')
                },
                hooks: {
                  click: function (elem, genLabel) {
                    return genLabel('单选框', $(elem).prop('checked') && '已选中')
                  },
                  mouseover: function (elem, genLabel) {
                    return genLabel('单选框', $(elem).prop('checked') && '已选中')
                  },
                },
              }
            },
            '.label_radio': function ($elems) {
              return {
                label: function (elem, genLabel) {
                  return genLabel('单选框', $(elem).hasClass('r_on') && '已选中', $(elem).text())
                },
                hooks: {
                  click: function (elem, genLabel) {
                    setAriaLabel($(elem).siblings('.label_radio'), function (elem, genLabel) {
                      return genLabel('单选框', $(elem).text())
                    })

                    return genLabel('单选框', $(elem).hasClass('r_on') && '已选中', $(elem).text())
                  },
                  // onTAB: function () {
                  //     return genLabel("单选框", $elem.hasClass("r_on") && "已选中", $elem.text())
                  // },
                  mouseover: function (elem, genLabel) {
                    return genLabel('单选框', $(elem).hasClass('r_on') && '已选中', $(elem).text())
                  },
                },
              }
            },
            ':file': function ($elems) {
              return {
                label: '文件选择按钮',
              }
            },
            ':button': function ($elems) {
              return {
                label: function (elem, genLabel) {
                  return genLabel('按钮', $(elem).is('input') ? $(elem).val() : $(elem).text())
                },
              }
            },
            ':submit': function ($elems) {
              return {
                label: function (elem, genLabel) {
                  return genLabel('提交按钮', $(elem).is('input') ? $(elem).val() : $(elem).text())
                },
              }
            },
            select: function ($elems) {
              return {
                label: function (elem, genLabel) {
                  const content = elem.options[elem.selectedIndex].innerText
                  return genLabel('下拉框', content)
                },
              }
            },
            textarea: function ($elems) {
              return {
                label: function (elem, genLabel) {
                  return genLabel('文本框', $(elem).val() || $(elem).attr('placeholder') || '')
                },
              }
            },
          }

          for (const selector in handlers) {
            const handler = handlers[selector]

            useHandler($(selector), handler)
          }

          function useHandler ($elems, handler) {
            $elems = $elems.not('[aria-label]')

            if (!$elems.length) return

            const options = handler($elems)

            setAriaLabel($elems, options.label, options.hooks)
          }
        }

        function handlePageSetting (createPageSetting) {
          if (typeof createPageSetting !== 'function') return

          const url = location.pathname + location.search
          const tool = {
            isIE: isIE,
            playImgCode: playImgCode,
            createStyle: createStyle,
            setInterval: setInterval,
            eventCenter: eventCenter,
            supportFocusAndMarkEvent: supportFocusAndMarkEvent,
            aria_label: setAriaLabel,
            play: playVoice,
          }
          const pageSetting = createPageSetting(tool, psmpArea, psmpTool)
          let isSet = false

          for (const pathInfo in pageSetting) {
            const handler = pageSetting[pathInfo]

            if (typeof handler !== 'function') continue

            const paths = pathInfo.split('|')

            for (let i = 0, len = paths.length; i < len; i++) {
              const path = paths[i]

              if (
                url.indexOf(path) === 0 ||
                path.match(/^TXCODE=\w+$/i) && url.match(new RegExp(RegExp.lastMatch, 'i'))
              ) {
                handler()
                isSet = true
              }
            }
          }

          return isSet
        }

        function setTabIndexForContent () {
          const Reg_Content = /[a-zA-Z0-9\u4e00-\u9fa5]/

          treeTraveller({
            onUpward: function (node, index, parent) {
              if (node.nodeType === 3 && node.textContent.match(Reg_Content) && !parent.getAttribute('tabindex') && !$(parent).hasClass('select_value')) {
                parent.setAttribute('tabindex', '0')
              }
            },
            children: function (node) {
              return node.childNodes
            },
          })(document.body)
        }

        function playImgCode (imgCodeElem) {
          if (typeof imgCodeElem === 'function') {
            imgCodeElem(createVoiceButton)
          } else if (imgCodeElem && imgCodeElem.nodeName.toLowerCase() === 'img') {
            createVoiceButton(imgCodeElem).insertAfter(imgCodeElem)
          } else {
            $('img[src^=\'/NCCB_Encoder/Encoder\'],img#valImg').each(function (i, elem) {
              createVoiceButton(elem).insertAfter(elem)
            })
          }

          function createVoiceButton (imgCodeElem) {
            const $imgCodeReader =
              $('<img aria-label="读出验证码" src="' + sourceHost + '/ccbctp/new/app_xyk/images/verifySpeak.gif" style="cursor: pointer;" alt="读出验证码" title="读出验证码">')
                .click(function (e) {
                  $('#encoder_sound').remove()

                  const codeMatcher = imgCodeElem.src.match(/code=([^&]+)/i)
                  const code = codeMatcher && codeMatcher[1]
                  const soundAddress = '/tran/ACVoiceJyServlet?lan=psmp_assist&code=' + code

                  if (isInFrame) {
                    winMessage.send(top, 'playSource', soundAddress, { immediate: true, forcePlay: true, })
                  } else {
                    psmpTool.voice.playSource(soundAddress, { immediate: true, forcePlay: true, })
                  }

                  e.stopPropagation()
                  e.preventDefault()
                })

            supportFocusAndMarkEvent($imgCodeReader[0], 'click')

            return $imgCodeReader
          }
        }

        function setImgCode () {
          playImgCode()

          mouseCapture.set('magnifyGlass', function (e) {
            if (!e.target) return

            showMagnifyGlassOfImgCode(e.target)
          })
        }

        function showMagnifyGlassOfImgCode (elem) {
          if (elem.nodeName === 'IMG' && ~elem.src.indexOf('/NCCB_Encoder/Encoder')) {
            psmpTool.magnifyGlass.set(elem, 2)
          } else if (elem !== psmpTool.magnifyGlass.canvas) {
            psmpTool.magnifyGlass.hide()
          }
        }

        function setIcon (name, iconClass) {
          const $icon = Button(name).find('.icon')
          $icon.removeClass().addClass('icon').addClass(iconClass)
        }

        function go (url, owin) {
          return function () {
            window.open(url, owin || '_self')
          }
        }
      })

      var setAriaLabel = (function () {
        return function (elemOrSelector, label, hooks) {
          const $elems = $(elemOrSelector)

          if (isPlainObject(label)) {
            hooks = label
            label = void 0
          }

          setAriaLabelAttr($elems, label)
          save($elems)

          if (!isPlainObject(hooks)) return

          if (hooks.onTAB) {
            $elems.keydown(restore)
            $elems.keyup(function (e) {
              if (e.keyCode !== 9) return

              setAriaLabelAttr(this, hooks.onTAB)
            })
          }

          if (hooks.mouseover) {
            $elems.mouseleave(restore)
            $elems.mouseover(function () {
              setAriaLabelAttr(this, hooks.mouseover)
            })
          }

          if (hooks.click) {
            $elems.click(function () {
              setAriaLabelAttr(this, hooks.click)
              save(this)
            })
          }
        }

        function genLabel () {
          const args = [].slice.call(arguments)
          return args.filter(Boolean).join(' ')
        }

        function setAriaLabelAttr (elems, labelOrFn) {
          if (labelOrFn == null) return

          $(elems).each(function (i, elem) {
            const $elem = $(elem)
            const label = isFunction(labelOrFn)
              ? labelOrFn(elem, genLabel)
              : labelOrFn === 'innerText'
                ? elem.innerText
                : labelOrFn

            if (label) {
              $elem.data('label-change', 1)
              $elem.attr('aria-label', label)
            }
          })
        }

        function save (elems) {
          $(elems).each(function (i, elem) {
            const $elem = $(elem)

            $elem.data('aria-label-raw', $elem.attr('aria-label'))
            $elem.data('label-change', void 0)
          })
        }

        function restore () {
          if ($(this).data('label-change') !== 1) return

          $(this).data('label-change', void 0)

          const label = $(this).data('aria-label-raw')
          $(this).attr('aria-label', isString(label) ? label : null)
        }
      }())

      var psmpArea = (function () {
        return {
          addNav: addArea('nav'),
          addWindow: addArea('window'),
          addInteractive: addArea('interactive'),
          addList: addArea('list'),
          addArticle: addArea('article'),
          addServis: addArea('servis'),
        }

        function addArea (areaName) {
          return function (selector) {
            $(selector).addClass('psmp_area_' + areaName)
            return this
          }
        }
      }())

      function Button (name) {
        const buttons = Button.buttons || (Button.buttons = {})
        if (buttons[name]) return buttons[name]

        const $btn = buttons[name] = $('.psmp_btn:contains(' + name + ')').add($('.psmp_btn[name=' + name + ']'))

        return $.extend($btn, {
          $on: function (fn) {
            if (typeof fn === 'function') {
              this._oncallback = fn
              this.click(function () {
                $btn.$state && typeof $btn._offcallback === 'function'
                  ? $btn.$off()
                  : $btn.$on()
              })
            } else if (typeof this._oncallback === 'function') {
              this._oncallback.call($btn)
              this.$state = true
            }
            return this
          },
          $off: function (fn) {
            if (typeof fn === 'function') {
              this._offcallback = fn
            } else if (typeof this._offcallback === 'function') {
              this._offcallback.call($btn)
              this.$state = false
            }
            return this
          },
          $default: function (fn) {
            if (typeof fn === 'function') {
              this._defaultCallback = fn
            } else if (typeof this._defaultCallback === 'function') {
              this._defaultCallback.call($btn)
            }
            return this
          },
        })
      }

      function interceptNative () {
        const oAlert = window.alert

        window.alert = function (message) {
          playVoice(message, {
            immediate: true,
            forcePlay: true,
            ondone: function (success) {
              if (!success) {
                oAlert(message)
              }
            },
          })
        }
      }

      function supportKeyboard () {
        const enter = ['click', 'mousedown', 'mouseup', 'mouseover', 'mouseenter']
        const esc = ['mouseout', 'mouseleave']
        const supportEvents = [].concat(enter, esc)

        /* 拦截jquery事件，添加tab触发器 */
        if (pageJquery) {
          eventCenter.listen('event$before', function (event, args) {
            if (!__SupportKeyboard__) return

            const $target = $(args[0])
            const eventType = args[1]
            const handler = args[2]
            const selector = args[4]

            if (
              !isString(eventType) ||
              !isFunction(handler) ||
              !~supportEvents.indexOf(eventType)
            ) {
              return
            }

            const $elems = isString(selector) ? $target.find(selector) : $target

            $elems.each(function (i, elem) {
              supportFocusAndMarkEvent(elem, eventType)
            })
          })
        }

        onDocReady(function () {
          /* 为通过元素属性绑定的事件，添加tab触发器 */
          setTimeout(function () {
            supportEvents.forEach(function (eventType) {
              $('[on' + eventType + ']').each(function (i, elem) {
                supportFocusAndMarkEvent(elem, eventType)
              })
            })
          }, 1000)

          let $focus = null
          let isPressEnter = null
          let isPressEsc = null

          /* 监听回车按键，触发事件 */
          $(document.body)
            .keydown(function (e) {
              const keyCode = e.keyCode

              /* ENTER & ESC */
              isPressEnter = keyCode === 13
              isPressEsc = keyCode === 27

              if (!isPressEnter && !isPressEsc) return $focus = null

              $focus = pageJquery ? pageJquery(':focus') : $(':focus')

              if ($focus.length) {
                e.stopPropagation()
              } else {
                $focus = null
              }
            })
            .keyup(function (e) {
              if (!$focus) return

              const keyboard_fires = $focus.attr('keyboard_fires')

              if (!keyboard_fires) return

              keyboard_fires.split(',').forEach(function (eventType) {
                if (isPressEnter && !~enter.indexOf(eventType)) return
                if (isPressEsc && !~esc.indexOf(eventType)) return

                if (eventType in $focus[0]) {
                  $focus[0][eventType]()
                } else {
                  $focus.trigger(eventType)
                }
              })

              $focus = isPressEnter = isPressEsc = null
            })
            .append('<style class="psmp_css">:focus{outline:2px solid red!important;outline-offset:-2px;}</style>')
        })
      }

      function supportFocusAndMarkEvent (elem, eventType) {
        elem === document && (elem = elem.documentElement)

        const $btn = $(elem).find('>a,>:input[type!=hidden]')

        if ($btn.length === 1 && getComputedStyle($btn[0]).display !== 'none' && ($(elem).children().length === 1 || isSameSize($(elem), $btn))) {
          elem = $btn[0]
        }

        if (elem.getAttribute('tabindex') < 0) return

        const isSupportTAB = $(elem).is('a[href],:input[type!=hidden]')
        isSupportTAB || elem.setAttribute('tabindex', '0')

        if (!eventType || (eventType === 'click' && isSupportTAB)) return

        let keyboard_fires = elem.getAttribute('keyboard_fires')

        if (keyboard_fires && ~keyboard_fires.indexOf(eventType)) return

        keyboard_fires = keyboard_fires
          ? [keyboard_fires, eventType].join(',')
          : eventType

        elem.setAttribute('keyboard_fires', keyboard_fires)

        processSpecialElem(elem)

        function processSpecialElem (elem) {
          const $elem = $(elem)

          if ($elem.is('.select_list')) {
            $elem.click(function () {
              $elem.find('.select_ul>ul>li:not([tabindex])').attr('tabindex', '0').attr('keyboard_fires', 'click')
            })
          } else if ($elem.is('[onclick^=laydate]')) {
            $elem.click(function () {
              setTimeout(function () {
                const $laydate_box = $('#laydate_box')

                if (!$laydate_box.attr('psmp_set')) {
                  $laydate_box.attr('psmp_set', '1')
                  $laydate_box.attr('tabindex', '0')
                  $laydate_box.find('.laydate_choose').click(function setTabIndexForTds () {
                    $laydate_box.find('#laydate_table td').attr('tabindex', '0').attr('keyboard_fires', 'click')
                      .filter('.laydate_nothis').attr('tabindex', null).attr('keyboard_fires', null)

                    return setTabIndexForTds
                  }())
                  $laydate_box.on('click', 'td', function () {
                    $elem.focus()
                  })
                }

                $laydate_box.focus()
              })
            })
          }
        }

        function isSameSize ($a, $b) {
          const sameWidth = $a[0].clientWidth === $b[0].clientWidth || $a.width() === $b.width()
          const sameHeight = $a[0].clientHeight === $b[0].clientHeight || $a.height() === $b.height()
          return sameWidth && sameHeight
        }
      }

      function playVoice (text, config) {
        if (isInFrame) {
          return new Promise(function (resolve) {
            let ondone

            if (config && config.ondone) {
              ondone = config.ondone
              delete config.ondone
            }

            winMessage.send(top, 'play', text, config).waitReply(function (_reply, success) {
              ondone && ondone(success)
              resolve(text)
            })
          })
        } else {
          return psmpTool.voice.play(text, config)
        }
      }

      function createpsmpTool () {
        let psmpTool
        const requires = [].slice.call(arguments)
        const hasRequired = requires.length
        const required = hasRequired
          ? requires.reduce(function (required, name) {
            required[name] = true
            return required
          }, {})
          : false

        function Zoom () {
          pageZoom = cssZoomFactor = this.zoom = 1
        }
        Zoom.prototype = {
          constructor: Zoom,
          reset: function () {
            this.setZoom(1)
          },
          increase: function (zoom) {
            if (zoom) {
              this.setZoom(zoom)
            } else {
              if (this.zoom >= 1.3) return
              const zoom = (parseFloat(this.zoom) + 0.1).toFixed(1)
              this.setZoom(zoom)
            }
          },
          decrease: function () {
            if (this.zoom <= 1) return
            const zoom = (parseFloat(this.zoom) - 0.1).toFixed(1)
            this.setZoom(zoom)
          },
          setZoom: function (zoom) {
            const $body = $('body')

            pageZoom = this.zoom = zoom

            if (useCssZoom) {
              cssZoomFactor = zoom
              $body.css({ zoom: zoom, overflow: 'scroll', })
              if (zoom == 1) {
                $body.css({ overflow: '', })
              }
            } else {
              cssScaleFactor = zoom
              $body.css({ transform: 'scale(' + zoom + ')', 'transform-origin': 'center top', })
            }

            psmpTool.cross_line && psmpTool.cross_line.draw()
          },
        }

        function Cursor () {
          this.cursor = null
        }
        Cursor.prototype = {
          constructor: Cursor,
          set: function (cursor) {
            if (cursor) {
              const cur_pic = 'psmp_assist/psmp/images/' + cursor + '.cur'
              createStyle('psmp_css_cursor', '', '*{cursor:url(' + cur_pic + '), auto!important}')
            } else {
              createStyle('psmp_css_cursor', '', '')
            }

            this.cursor = cursor
          },
          reset: function () {
            this.set(null)
          },
        }

        function CrossLine () {
          this.h_line = null
          this.v_line = null
          this.clientX = this.clientY = null
        }
        CrossLine.prototype = {
          constructor: CrossLine,
          draw: function (clientX, clientY) {
            if (clientX == void 0) clientX = this.clientX
            if (clientY == void 0) clientY = this.clientY

            const top = (clientY - 10) / cssZoomFactor
            const left = (clientX - 10) / cssZoomFactor

            this.h_line && this.h_line.css('top', top)
            this.v_line && this.v_line.css('left', left)
            this.clientX = clientX
            this.clientY = clientY
          },
          show: function () {
            const $body = $('body')
            this.h_line = $('<div id=\'slideLateral\'></div>')
            this.v_line = $('<div id=\'slideLongitudinal\'></div>')
            $body.append(this.h_line)
            $body.append(this.v_line)
          },
          hide: function () {
            this.h_line.remove()
            this.v_line.remove()
            this.h_line = this.v_line = null
          },
        }

        function Theme () { }
        Theme.prototype = {
          constructor: Theme,
          set: function (background, color) {
            this.exclude('.header_box .logo img,#fsD1,.psmp_elem,#slideLateral,#slideLongitudinal')
            $('.header_box .logo img').css({ 'background-color': '#ffffff', })

            const $body = $('body')
            const shouldSet = background || color

            background = background || 'none'
            color = color || 'none'

            $body.find(':not(.psmp_theme,.psmp_theme_exclude_self,.psmp_theme_exclude,.psmp_theme_exclude *)').add($body).each(function () {
              $(this).addClass('psmp_theme')
            })

            const cssText = shouldSet
              ? '.psmp_theme{background-color:' + background + '!important;color:' + color + '!important;background-image:none!important;}'
              : '.psmp_theme{}'

            createStyle('psmp_css_theme', 'psmp_css', cssText)
          },
          excludeSelf: function (selector) {
            $(selector).addClass('psmp_theme_exclude_self')
          },
          exclude: function (selector) {
            $(selector).addClass('psmp_theme_exclude')
          },
        }

        function TextScreen () {
          this.$elem = null
          this.isVisible = false
        }
        TextScreen.prototype = {
          constructor: TextScreen,
          init: function () {
            const $bigText_html = $('<div id="psmp_view_bigtext" class="psmp_elem psmp_zoom"><div class="TextScreen" id="TextScreen"><div class="BTnav"><div class="highContrast"><a href="javascript:void(0)" theme="#fff_#000" id="bigtext_hic_t1">白底黑字</a><a href="javascript:void(0)" theme="#00f_#ff0" id="bigtext_hic_t2">蓝底黄字</a><a href="javascript:void(0)" theme="#ff0_#000" id="bigtext_hic_t3">黄底黑字</a><a href="javascript:void(0)" theme="#000_#ff0" id="bigtext_hic_t4">黑底黄字</a><a href="javascript:void(0)" id="bigtext_hic_t5">默认配色</a></div></div><div class="BTcont" id="BTcont"><h1 id="BigText_Content"></h1></div></div></div>')
            $('body').append($bigText_html)
            this.$elem = $bigText_html

            $('.highContrast', this.$elem).on('click', 'a', function () {
              const theme = ($(this).attr('theme') || '_').split('_')
              const background = theme[0]
              const color = theme[1]
              $('#BTcont,#BigText_Content').css({ 'background-color': background, color: color, })
            })
          },
          toggle: function () {
            this.isVisible ? this.hide() : this.show()
          },
          show: function () {
            if (!this.$elem) this.init()
            this.$elem.show()
            this.isVisible = true
          },
          hide: function () {
            this.$elem && this.$elem.hide()
            this.isVisible = false
          },
          setText: function (text) {
            $('#BigText_Content', this.$elem).text(text)
          },
        }

        function MagnifyGlass () {
          this.$canvas = this.canvas = this.ctx = null
        }
        MagnifyGlass.prototype = {
          constructor: MagnifyGlass,
          init: function () {
            const _self = this

            this.$canvas = $('<canvas id="magnify_glass" class="psmp_elem"></canvas>')
              .appendTo('body')
              .hide()
              .css({
                position: 'absolute',
                padding: '3px',
                border: '1px solid #02599c',
                'box-shadow': '2px 2px 6px #666',
                'border-radius': '8px',
                background: '#fff',
              })
              .click(function () {
                $(_self.img).click()
                _self.drawImage()
              })

            this.canvas = this.$canvas[0]
            this.ctx = this.canvas.getContext('2d')
          },
          set: function (img, scale) {
            if (!this.canvas) this.init()

            scale = scale || 2

            const pos = offset(img)
            const imgW = $(img).width()
            const imgH = $(img).height()
            const width = imgW * scale
            const height = imgH * scale
            const x = pos.left - (width - imgW) / 2
            const y = pos.top - (height - imgH) / 2

            this.img = img
            this.canvas.width = width
            this.canvas.height = height

            this.drawImage = drawImage
            this.drawImage()

            this.$canvas.css({
              top: y,
              left: x,
            }).show()

            let _src
            function drawImage () {
              const _self = this
              const src = img.src

              if (!_src) {
                this.ctx.drawImage(img, 0, 0, width, height)
              } else {
                img.onload = function () {
                  _self.ctx.drawImage(img, 0, 0, width, height)
                  img.onload = null
                }
              }

              _src = src
              return true
            }
          },
          hide: function () {
            this.$canvas && this.$canvas.hide()
          },
        }

        if (!required) {
          return psmpTool = {
            zoom: new Zoom(),
            cursor: new Cursor(),
            cross_line: new CrossLine(),
            theme: new Theme(),
            textScreen: new TextScreen(),
            voice: new Classes.Voice(),
            magnifyGlass: new MagnifyGlass(),
          }
        } else {
          return psmpTool = {
            zoom: required.zoom && new Zoom() || null,
            cursor: required.cursor && new Cursor(),
            cross_line: required.cross_line && new CrossLine(),
            theme: required.theme && new Theme(),
            textScreen: required.textScreen && new TextScreen(),
            voice: required.voice && new Classes.Voice(),
            magnifyGlass: required.magnifyGlass && new MagnifyGlass(),
          }
        }
      }

      function getFrameElem (target) {
        return $('iframe').toArray().filter(function (frame) {
          return frame.contentWindow === target
        })[0]
      }

      function join () {
        const strs = [].slice.call(arguments).filter(Boolean)

        return strs.join()
      }

      function offset (elem) {
        let left = 0; let top = 0

        while (elem) {
          left += elem.offsetLeft
          top += elem.offsetTop
          elem = elem.offsetParent
        }

        return { left: left, top: top, }
      }

      function createStyle (id, className, cssText) {
        className = className || ''

        let $style = $('#' + id)

        if (!$style.length) {
          $style = $('<style id="' + id + '">' + cssText + '</style>').prependTo('body')
          className && $style.attr('class', className)
        } else {
          $style.text(cssText)
        }
      }

      function debounce (fn, wait, immediate) {
        let timer, args, context, timestamp
        function later () {
          const last = +new Date() - timestamp
          if (last < wait) {
            timer = setTimeout(later, wait - last)
          } else {
            timer = null
            if (!immediate) {
              fn.apply(context, args)
              context = args = null
            }
          }
        }
        return function () {
          context = this, args = arguments, timestamp = +new Date()
          const callNow = immediate && !timer
          if (!timer) {
            timer = setTimeout(later, wait)
          }
          if (callNow) {
            fn.apply(context, args)
            context = args = null
          } else if (typeof immediate === 'function') {
            immediate()
          }
        }
      }

      function throttle (fn, interval) {
        let timer; let lastTimestamp = 0

        return function () {
          if (timer) return

          const _self = this
          const args = arguments
          const timestamp = +new Date()
          const pastTime = timestamp - lastTimestamp

          if (pastTime >= interval) {
            lastTimestamp = timestamp
            fn.apply(_self, args)
          } else {
            timer = setTimeout(function () {
              timer = null
              lastTimestamp = +new Date()
              fn.apply(_self, args)
            }, interval - pastTime)
          }
        }
      }

      function treeTraveller (config) {
        const onDownward = isFunction(config.onDownward) ? config.onDownward : null
        const onUpward = isFunction(config.onUpward) ? config.onUpward : null
        const getChildren = isFunction(config.children) ? config.children : null

        return function read (node, index, parent) {
          onDownward && onDownward(node, index, parent)

          const children = getChildren ? getChildren(node) : node.children

          if (children) {
            for (let i = 0, len = children.length; i < len; i++) {
              const child = children[i]
              read(child, i, node)
            }
          }

          onUpward && onUpward(node, index, parent)
        }
      }
    }
  }(function () {
    function Shortcut (keyupCapture) {
      this.listener = null
      this.callbacks = {}
      this.keyupCapture = keyupCapture
    }

    Shortcut.prototype = {
      constructor: Shortcut,
      keyMap: { 0: 48, 1: 49, 2: 50, 3: 51, 4: 52, 5: 53, 6: 54, 7: 55, 8: 56, 9: 57, '`': 192, '-': 189, '=': 187, backspace: 8, tab: 9, q: 81, w: 87, e: 69, r: 82, t: 84, y: 89, u: 85, i: 73, o: 79, p: 80, '[': 219, ']': 221, '\\': 220, a: 65, s: 83, d: 68, f: 70, g: 71, h: 72, j: 74, k: 75, l: 76, ';': 186, '\'': 222, enter: 13, z: 90, x: 88, c: 67, v: 86, b: 66, n: 78, m: 77, ',': 188, '.': 190, '/': 191, space: 32, arrowleft: 37, arrowright: 39, arrowup: 38, arrowdown: 40, },
      unshift: {
        '~': '`',
        '!': '1',
        '@': '2',
        '#': '3',
        $: '4',
        '%': '5',
        '^': '6',
        '&': '7',
        '*': '8',
        '(': '9',
        ')': '0',
        _: '-',
        '+': '=',
        '{': '[',
        '}': ']',
        '|': '\\',
        ':': ';',
        '"': '\'',
        '<': ',',
        '>': '.',
        '?': '/',
      },
      normalizeKey: function (shortcut_key) {
        const keys = shortcut_key.toLowerCase().split(/\s*\+\s*/)
        const modifierKey = []
        let mainKey = ''

        keys.forEach(function (key) {
          if (key === 'ctrl' || key === 'alt' || key === 'shift') return modifierKey.push(key)

          key = key in this.unshift ? this.unshift[key] : key
          mainKey = key in this.keyMap ? '[' + this.keyMap[key] + ']' : key
        }, this)

        return modifierKey.sort().concat(mainKey).join('+')
      },
      set: function (shortcut_key, fn) {
        if (!isFunction(fn)) return

        const key = this.normalizeKey(shortcut_key)

        if (this.callbacks[key]) throw '快捷键冲突：' + shortcut_key

        this.callbacks[key] = fn

        this.start()
      },
      remove: function (shortcut_key) {
        const key = this.normalizeKey(shortcut_key)

        this.callbacks[key] = null
        delete this.callbacks[key]

        if (!Object.keys(this.callbacks).length) {
          this.stop()
        }
      },
      start: function () {
        if (this.listener) return

        const callbacks = this.callbacks

        this.listener = function (e) {
          const key = e.key.toLowerCase()
          if (~['alt', 'ctrl', 'shift'].indexOf(key)) return

          const modifierKey = []

          e.altKey && modifierKey.push('alt')
          e.ctrlKey && modifierKey.push('ctrl')
          e.shiftKey && modifierKey.push('shift')

          const shortcut_key = modifierKey.concat('[' + e.keyCode + ']').join('+')
          callbacks[shortcut_key] && callbacks[shortcut_key]()
        }

        this.keyupCapture.set('shortcut', this.listener)
      },
      stop: function () {
        if (!this.listener) return

        this.keyupCapture.remove('shortcut')
        this.listener = null
      },
    }

    function WindowMessage () {
      this._replyReceivers = {}
      this._commandHandlers = {}
      this._addMessageLinster()
      this._addReplyCommand()
    }

    WindowMessage.prototype = {
      constructor: WindowMessage,
      _separator: '|@|',
      _cmd$reply: '@reply',
      _addMessageLinster: function () {
        const _self = this
        window.addEventListener('message', function (e) {
          if (0 && !_self._isWhiteHost(e.origin)) return console.error('不是有效的消息来源')
          // var msg = typeof e.data === "string" ? e.data.split(_self._separator) : e.data,
          //     command = msg[0],
          //     msgId = msg[1],
          //     commandArgs = msg.slice(2)
          //
          // if (command && msgId)
          //     _self._execCommand(command, e, msgId, commandArgs)
        })
      },
      _isWhiteHost: function (host) {
        return /https?:\/\/([^.]+\.)*ccb.com/.test(host)
      },
      _execCommand: function (command, e, msgId, commandArgs) {
        const _self = this
        const sourceWin = e.source
        const commandHandler = this._commandHandlers[command]

        if (typeof commandHandler !== 'function') return

        reply.target = sourceWin
        commandArgs = [reply].concat(commandArgs)

        commandHandler.apply(null, commandArgs)

        function reply () {
          const replyArgs = [].slice.call(arguments)
          const args = [sourceWin, _self._cmd$reply].concat(msgId, replyArgs)
          return _self.send.apply(_self, args)
        }
      },
      _addReplyCommand: function () {
        const _self = this
        this.addCommandHandler(this._cmd$reply, function (reply, msgId) {
          if (typeof _self._replyReceivers[msgId] !== 'function') return

          const replyArgs = [].slice.call(arguments, 2)
          _self._replyReceivers[msgId].apply(null, [reply].concat(replyArgs))
          _self._replyReceivers[msgId] = null
        })
      },
      addCommandHandler: function (command, handler) {
        if (this._commandHandlers[command]) throw Error(command + ' 命令已占用')
        this._commandHandlers[command] = handler
      },
      send: function (targetWindow, command) {
        const _self = this
        const commandArgs = [].slice.call(arguments, 2)
        const msgId = +new Date() + '' + (Math.random() * 1000 | 0)
        const msg = [command, msgId].concat(commandArgs)

        targetWindow.postMessage(msg, '*')

        return {
          waitReply: function (replyReceiver) {
            if (typeof replyReceiver !== 'function') throw Error('参数必须是函数')
            _self._replyReceivers[msgId] = replyReceiver
          },
        }
      },
    }

    function EventCapture (eventType, selector) {
      this.eventType = eventType
      this.listener = null
      this.callbacks = {}
      this.selector = selector || document
    }

    EventCapture.prototype = {
      set: function (key, fn) {
        if (typeof fn !== 'function' || this.callbacks[key]) return

        this.callbacks[key] = fn
        this.start()
      },
      remove: function (key) {
        this.callbacks[key] = null
        delete this.callbacks[key]

        if (!Object.keys(this.callbacks).length) {
          this.stop()
        }
      },
      has: function (key) {
        return this.callbacks.hasOwnProperty(key)
      },
      trigger: function (keys, data) {
        if (!this.listener) return

        const callbacks = this.callbacks

        if (keys === 'all') {
          keys = Object.keys(callbacks)
        } else {
          keys = typeof keys === 'string' ? [keys] : keys
        }

        keys.forEach(function (key) {
          if (callbacks.hasOwnProperty(key)) {
            callbacks[key](data)
          }
        })
      },
      start: function () {
        if (this.listener) return

        const callbacks = this.callbacks

        this.listener = function (e) {
          for (const key in callbacks) {
            if (callbacks.hasOwnProperty(key)) {
              callbacks[key](e)
            }
          }
        }

        __SupportKeyboard__ = false

        $(this.selector).on(this.eventType, this.listener)

        __SupportKeyboard__ = true
      },
      stop: function () {
        if (!this.listener) return

        $(this.selector).off(this.eventType, this.listener)
        this.listener = null
      },
    }

    function EventCenter (config) {
      const defConfig = {
        useOffline: false,
        context: null,
      }

      this.config = Object.assign({}, defConfig, config)
      this.cache = {}
      this.offlineActionsMap = {}
    }

    EventCenter.prototype = {
      constructor: EventCenter,
      listen: function (key, fn, context, last) {
        const cache = this.cache
        const listener = { fn: fn, context: context, }

        if (!cache[key]) cache[key] = []

        cache[key].push(listener)
        this.config.useOffline && this._triggerOffline(key, last)
      },
      trigger: function (key) {
        const _self = this
        const args = [].slice.call(arguments)
        const action = function () {
          _self._trigger.apply(_self, args)
        }

        this.cache[key] ? action() : (this.config.useOffline && this._addOffline(key, action))
      },
      remove: function (key, fn) {
        const _self = this

        if (this._trigging) return void setTimeout(function () { _self.remove(key, fn) })

        const cache = this.cache; const listeners = cache[key]

        if (listeners) {
          if (fn) {
            for (let i = 0, len = listeners.length; i < len; i++) {
              const listener = listeners[i]

              if (listener.fn === fn) {
                listeners.splice(i, 1)
                break
              }
            }
          } else {
            cache[key] = null
          }
        }
      },
      _trigger: function (key) {
        const args = [].slice.call(arguments, 1)
        const listeners = this.cache[key]
        const gContext = this.config.context

        if (!listeners || !listeners.length) return

        this._trigging = true
        listeners.forEach(function (listener) {
          const fn = listener.fn
          let context = listener.context || gContext || window

          typeof context === 'function' && (context = context())
          fn.apply(context, args)
        })
        this._trigging = false
      },
      _addOffline: function (key, action) {
        const offlineActions = this.offlineActionsMap[key] || (this.offlineActionsMap[key] = [])

        offlineActions.push(action)
      },
      _triggerOffline: function (key, last) {
        const offlineActions = this.offlineActionsMap[key]

        if (!offlineActions) return

        if (last === 'last') {
          offlineActions.length && offlineActions.pop()()
        } else {
          offlineActions.forEach(function (offlineAction) {
            offlineAction()
          })
        }

        this.offlineActionsMap[key] = null
      },
    }

    function Voice () {
      this.speed = '0'// 语速

      this.audio = null
      this.session = null
      this.allowPlay = true
      this.playing = null
    }

    Voice.prototype = {
      constructor: Voice,
      speedReset: function () {
        this.speed = '0'
      },
      speedUp: function () {
        this.speed = Math.min(parseFloat(this.speed) + 100, 500).toFixed(0)
      },
      speedDown: function () {
        this.speed = Math.max(parseFloat(this.speed) - 100, -500).toFixed(0)
      },
      on: function () {
        this.allowPlay = true
      },
      off: function () {
        this.allowPlay = false
        this.pause()
      },
      pause: function () {
        this.audio && this.audio.pause()
        this.rejectQueueBlock && this.rejectQueueBlock()
        this.voice_id = this.queue = this.playInfo = this.rejectQueueBlock = null
      },
      playSource: function (src, config) {
        const playInfo = this._genPlayInfo(src, config)

        if (!this._canPlay(playInfo)) return Promise.resolve(playInfo.done(false))

        this.pause()

        this.playInfo = playInfo

        const voice = this

        return this._playSource(src)
          .then(function () {
            voice.playInfo = null

            return playInfo.done(true)
          })
      },
      play: function (text, config) {
        const playInfo = this._genPlayInfo(text, config)

        if (
          text == void 0 ||
          !/[a-zA-Z0-9\u4e00-\u9fa5]/.test(text) ||
          !this._canPlay(playInfo)
        ) return Promise.resolve(playInfo.done(false))

        this.pause()

        this.playInfo = playInfo

        const voice = this
        const sentences = splitText(text)
        const voice_id = this.voice_id = Math.random()
        const _await = Promise.resolve()
        let block = false
        const maxIndex = sentences.length - 1

        return sentences
          .reduce(function (_await, sentence, i) {
            return _await.then(function () {
              if (voice_id !== voice.voice_id) return


              return voice._getSource(sentence).then(function (src) {

                if (voice_id !== voice.voice_id) return

                const shouldCreateBlock = !block && i < maxIndex - 1 && voice.queue && voice.queue.length >= 1

                if (shouldCreateBlock) {
                  /*
                                    创建阻塞，当这条语音播放完毕后解除阻塞，继续请求余下的文本语音。
                                    阻塞会在后一个请求使用，使后一个请求的语音作为解除阻塞后的缓冲
                                    */

                  block = new Promise(function (resolve, reject) {
                    voice.rejectQueueBlock = reject
                    voice._queueSource(src, function onSourceEnd () {
                      block = false
                      voice.rejectQueueBlock = null
                      resolve()
                    })
                  })
                } else {
                  voice._queueSource(src)

                  if (block) {
                    return block
                  }
                }
              })
            })
          }, _await).catch(function () { })
          .then(function () {
            /** 等待队列中所有语音播放完毕 */
            return voice.queue && voice.queue.cosumer
          })
          .then(function () {
            voice.playInfo = null

            return playInfo.done(true)
          })
      },
      _genPlayInfo: function (content, config) {
        config || (config = {})

        return {
          content: content,
          forcePlay: Boolean(config.forcePlay),
          immediate: Boolean(config.immediate),
          done: function (playState) {
            config.ondone && config.ondone(playState)

            return content
          },
        }
      },
      _canPlay: function (newPlayInfo) {
        const canPlay = this.allowPlay && (!this.playInfo || !this.playInfo.forcePlay || newPlayInfo.immediate)

        if (canPlay) return canPlay

        this.allowPlay
          ? console.log('当前语音为强制播放，不能中断。')
          : console.log('声音已关闭，取消播放。')

        return canPlay
      },
      _getSource: function (text) {
        const param = { spd: this.speed, }

        return new Promise(function (resolve, reject) {
          // TODO 替代播放源
          playAudio(text, param, function (err, src) {
            err ? reject(err) : resolve(src)
          })
          // getSource(text, param, function (err, src) {
          //   err ? reject(err) : resolve(src)
          // })
        })
      },
      _queueSource: function (src, onSourceEnd) {
        const voice = this
        const queue = this.queue || (this.queue = [])

        queue.push([src, onSourceEnd])


        if (!queue.cosumer) {
          queue.cosumer = consume().then(function () {
            voice.queue = null
          })
        }

        return queue.cosumer

        function consume () {
          const queue = voice.queue
          const voiceInfo = queue && queue.shift()

          if (!voiceInfo) return Promise.resolve()

          const src = voiceInfo[0]
          const onSourceEnd = voiceInfo[1]


          return voice._playSource(src).then(function () {
            typeof onSourceEnd === 'function' && onSourceEnd()

            if (!queue.length) return

            return consume()
          })
        }
      },
      _playSource: function (src) {
        const voice = this

        return new Promise(function (resolve) {

          if (isIE) {
            var audio = voice.audio = $('<embed src="' + src + '" hidden="true" id="voice_player"></embed>')[0]

            setInterval(function () {
              const playState = audio.PlayState

              if (playState <= 1) {
                audio.onpause && audio.onpause()
              }

              return playState <= 1
            }, 100)
          } else {
            var audio = voice.audio

            if (!audio) {
              audio = voice.audio = document.createElement('audio')
              $('body').append(audio)
            }

            audio.src = src

            audio.play().catch(function (err) {
              resolve()
            })
          }

          audio.onpause = function () {
            resolve()
          }
        })
      },
    }

    const namespace = 'psmp-assist'
    const domain = ''
    // const url = 'https://tts.baidu.com/text2audio' // baidu
    // const url = 'https://fanyi.sogou.com/reventondc/synthesis' // sougou
    // const url = 'http://tts.youdao.com/fanyivoice' // youdao
    const url = 'https://api.vvhan.com/api/song'
    const config = {
      namespace: namespace,
      domain: domain,
      url: url,
    }

    function playAudio (text, param, callback) {
      // TODO 实现方式一（取决于浏览器和操作系统）
      if (window.SpeechSynthesisUtterance) {
        // google播放卡主无声音需先取消再播放
        window.speechSynthesis.cancel()
        const utterThis = new SpeechSynthesisUtterance()
        utterThis.text = text
        utterThis.lang = 'zh' // 汉语
        // utterThis.rate = 0.7; // 语速
        if (param.spd === '0') {
          utterThis.rate = 0.7
        } else {
          utterThis.rate = Number(param.spd) / 100 // 语速
        }
        speechSynthesis.speak(utterThis)
      } else { // 不支持 SpeechSynthesis API
        // TODO 实现方式一 TODO TTS audio 实现
        const audio = document.getElementById('tts_autio_id') || ''
        if (!audio) {
          return
        }
        const AudioApi = config.url

        // sougou
        // const __speed = 1
        // const AudioParam = 'lang=zh-CHS&from=translateweb&speaker=6&speed='.concat(__speed, '&text=').concat(encodeURI(text))

        // baidu
        // const __speed = 5
        // const AudioParam = 'cuid=baike&lan=ZH&ctp=1&pdt=301&vol=9&rate=32&ie=UTF-8&per=0&spd='.concat(__speed, '&tex=').concat(encodeURI(text))

        // youdao
        // const AudioParam = 'le=zh&keyfrom=speaker-target&word='.concat(encodeURI(text)) // word要转换的文本le 语言类型 zh 中文 en 英文

        const AudioParam = 'txt='.concat(encodeURI(text)).concat('&per=5')

        const AudioUrl = ''.concat(AudioApi, '?').concat(AudioParam)
        audio.src = AudioUrl
        audio.crossOrigin = 'anonymous'
        document.getElementById(''.concat(namespace, '-audio-source')).src = AudioUrl
        document.getElementById(''.concat(namespace, '-audio-embed')).src = AudioUrl
        const playPromise = audio.play()
        if (isFunction(callback)) {
          callback(0, AudioUrl)
        }
        if (playPromise) {
          playPromise.then(function (_) {
            // audio.pause();
            // removeEvent(document, 'click', forceSafariPlayAudio)
          }).catch(function (error) {
          })
        }
      }
    }

    function splitText (text) {
      if (text.length <= 30) return [text]

      return text.split(/，|。|\n/).map(function (text) { return text.trim() }).filter(Boolean)
    }

    function Config () {
      const psmp_config = getCookie('psmp_config')
      this._config = psmp_config ? JSON.parse(psmp_config) : {}
    }

    Config.prototype = {
      set: function (name, value) {
        value === false
          ? delete this._config[name]
          : this._config[name] = value

        setCookie('psmp_config', JSON.stringify(this._config))
      },
      get: function (name) {
        return name ? this._config[name] : Object.assign({}, this._config)
      },
    }

    return {
      Shortcut: Shortcut,
      WindowMessage: WindowMessage,
      EventCapture: EventCapture,
      EventCenter: EventCenter,
      Voice: Voice,
      Config: Config,
    }
  }()))

  /**
   * jquery 的 $(document).ready(callback) 会因为 callback 抛出异常导致内部状态没重置为 false 而失效
   * onDocReady 会确保 callback 总是异步调用
   */
  function onDocReady (callback) {
    let release = null

    if (onDocReady.isDocReady || document.readyState === 'complete') {
      return void setTimeout(ready)
    }

    if (document.addEventListener) {
      document.addEventListener('DOMContentLoaded', ready, false)

      release = function () {
        document.removeEventListener('DOMContentLoaded', ready)
      }
    } else if (window.doScroll && window.frameElement == null) {
      const doc = document.documentElement

      !(function doScroll () {
        try {
          doc.doScroll('left')
        } catch (err) {
          return setTimeout(doScroll, 30)
        }

        ready()
      }())
    } else {
      /**
       * 没有 doScroll 或 iframe 内不能使用 doScroll 时
       */
      document.attachEvent('onreadystatechange', onready)

      release = function () {
        document.detachEvent('onreadystatechange', onready)
      }

      function onready () {
        if (document.readyState === 'complete') {
          ready()
        }
      }
    }

    function ready () {
      onDocReady.isDocReady = true

      release && release()
      callback && callback()
    }
  }

  function setCookie (key, value, expires) {
    document.cookie = key + '=' + encodeURIComponent(value) + ';domain=' + location.host + ';path=/;' + (expires || '')
  }

  function getCookie (key) {
    const reg = new RegExp(key + '=([^;]+)')
    const cookie = document.cookie.match(reg)
    return cookie && decodeURIComponent(cookie[1])
  }

  function removeCookie (key) {
    const value = getCookie(key)
    const date = new Date()
    date.setTime(date.getTime() - 1)
    const dateGMT = date.toGMTString()
    setCookie(key, value, 'expires=' + dateGMT + ';')
  }

  function setInterval (fn, interval, duration) {
    let timestamp = +new Date()
    var sid = window.setInterval(function () {
      if (duration && +new Date() - timestamp >= duration || fn() === true) {
        clearInterval(sid)
        timestamp = sid = void 0
      }
    }, interval)
  }

  function isPlainObject (a) {
    return isObject(a) && a.constructor === Object
  }

  function isObject (a) {
    return a !== null && typeof a === 'object'
  }

  function isArray (a) {
    return a instanceof Array
  }

  function isBoolean (a) {
    return typeof a === 'boolean'
  }

  function isFunction (a) {
    return typeof a === 'function'
  }

  function isString (a) {
    return typeof a === 'string'
  }
}())
