package com.ddev.myapplication.util

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.DisplayMetrics
import android.view.*
import androidx.databinding.DataBindingUtil
import com.ddev.myapplication.R
import com.ddev.myapplication.databinding.CustomDialogLayoutBinding

class CustomAlertDialog: Dialog {

    private var dialogLayoutBinding: CustomDialogLayoutBinding? = null
    private var rootView: ViewGroup? = null
    private var activity: Activity? = null
    //private var displayMetrics: DisplayMetrics? = null
    private var confirmationClickListener: View.OnClickListener? = null
    private var closeClickListener: View.OnClickListener? = null


    constructor (activity: Activity, rootView: ViewGroup?) : super(activity) {
        this.activity = activity
        this.rootView = rootView
        doConfig()
    }

    private fun doConfig() {
        this.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

//        displayMetrics = DisplayMetrics()
//        (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getMetrics(
//            displayMetrics
//        )

        dialogLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.custom_dialog_layout,
            null,
            false
        )

        dialogLayoutBinding!!.dialogCardView.minimumWidth = context.resources.displayMetrics.widthPixels

        dialogLayoutBinding!!.dialogCloseIcon.visibility = View.GONE
        dialogLayoutBinding!!.dialogImage.visibility = View.GONE
        dialogLayoutBinding!!.dialogTitle.visibility = View.GONE
        dialogLayoutBinding!!.dialogSubtitle.visibility = View.GONE
        dialogLayoutBinding!!.dialogConfirmBtn.visibility = View.GONE

        dialogLayoutBinding!!.dialogConfirmBtn.setOnClickListener { view ->
            confirmationClickListener?.onClick(dialogLayoutBinding!!.dialogConfirmBtn)
        }

        dialogLayoutBinding!!.dialogCloseIcon.setOnClickListener { view ->
            closeClickListener?.onClick(dialogLayoutBinding!!.dialogCloseIcon)
        }

        setContentView(dialogLayoutBinding!!.root)
    }

    fun setImage(imageId: Int) {
        dialogLayoutBinding!!.dialogImage.setImageResource(imageId)
        dialogLayoutBinding!!.dialogImage.visibility = View.VISIBLE
    }

    fun setCardBackground(colorId: Int) {
        dialogLayoutBinding!!.dialogCardView.setCardBackgroundColor(
            activity!!.resources.getColor(
                colorId
            )
        )
    }

    fun setPositiveButtonStyle(btnBackground: Int, btnText: String?, textColor: Int) {
        dialogLayoutBinding!!.dialogConfirmBtn.text = btnText
        dialogLayoutBinding!!.dialogConfirmBtn.setTextColor(activity!!.resources.getColor(textColor))
        dialogLayoutBinding!!.dialogConfirmBtn.setBackgroundResource(btnBackground)
        dialogLayoutBinding!!.dialogConfirmBtn.visibility = View.VISIBLE
    }

    fun setCloseIconVisible(isVisible: Boolean) {
        dialogLayoutBinding!!.dialogCloseIcon.visibility =
            if (isVisible) View.VISIBLE else View.GONE
    }

    fun setTitle(title: String?, textColor: Int) {
        dialogLayoutBinding!!.dialogTitle.text = title
        dialogLayoutBinding!!.dialogTitle.setTextColor(activity!!.resources.getColor(textColor))
        dialogLayoutBinding!!.dialogTitle.visibility = View.VISIBLE
    }

    fun setSubTitle(subTitle: String?, textColor: Int) {
        val strings = ArrayList<String?>()
        strings.add(subTitle)
        setSubTitle(strings, textColor)
    }

    private fun setSubTitle(subTitles: ArrayList<String?>?, textColor: Int) {
        var finalSubtitle: String? = String()
        if (subTitles != null && subTitles.size == 0) {
            dialogLayoutBinding!!.dialogSubtitle.visibility = View.GONE
        } else {
            if (subTitles!!.size == 1) {
                finalSubtitle = subTitles[0]
                dialogLayoutBinding!!.dialogTitle.textAlignment = View.TEXT_ALIGNMENT_CENTER
                dialogLayoutBinding!!.dialogTitle.gravity = Gravity.CENTER
                dialogLayoutBinding!!.dialogSubtitle.textAlignment = View.TEXT_ALIGNMENT_CENTER
                dialogLayoutBinding!!.dialogSubtitle.gravity = Gravity.CENTER
            } else if (subTitles.size > 1) {
                var isFirst = true
                for (subTitle in subTitles) {
                    finalSubtitle += (if (!isFirst) "\n\n" else "") + "-" + subTitle
                    isFirst = false
                }
                dialogLayoutBinding!!.dialogTitle.textAlignment = View.TEXT_ALIGNMENT_VIEW_START
                dialogLayoutBinding!!.dialogTitle.gravity = Gravity.START
                dialogLayoutBinding!!.dialogSubtitle.textAlignment = View.TEXT_ALIGNMENT_VIEW_START
                dialogLayoutBinding!!.dialogSubtitle.gravity = Gravity.START
            }
            dialogLayoutBinding!!.dialogSubtitle.text = finalSubtitle
            dialogLayoutBinding!!.dialogSubtitle.setTextColor(
                activity!!.resources.getColor(
                    textColor
                )
            )
            dialogLayoutBinding!!.dialogSubtitle.visibility = View.VISIBLE
        }
    }

    fun setOnConfirmationBtnClickListener(clickListener: View.OnClickListener?) {
        confirmationClickListener = clickListener
    }

    fun setOnCloseBtnClickListener(clickListener: View.OnClickListener?) {
        closeClickListener = clickListener
    }

    override fun show() {
        super.show()
    }

    override fun dismiss() {
        super.dismiss()
    }

    fun showSuccess(title: String?, message: String?) {
        setTitle(title, R.color.checkout_success_color)
        setSubTitle(message, R.color.login_text_color)
        setImage(R.drawable.successdialogicon)
        setCardBackground(R.color.white)
        setPositiveButtonStyle(
            R.color.checkout_success_color,
            activity!!.getString(R.string.done_string),
            R.color.black
        )
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        setOnConfirmationBtnClickListener { view: View? -> dismiss() }
        show()
    }

    fun showFailResponse(title: String?, message: ArrayList<String?>) {
        setTitle(title, R.color.checkout_success_color)
        setSubTitle(message, R.color.login_text_color)
        setCardBackground(R.color.white)
        setImage(R.drawable.faileddialogicon)
        setPositiveButtonStyle(
            R.color.checkout_success_color,
            activity!!.getString(R.string.try_again_string),
            R.color.black
        )
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        setOnConfirmationBtnClickListener { view: View? -> dismiss() }
        show()
    }

    fun showFailResponse(title: String?, message: String?) {
        setTitle(title, R.color.checkout_success_color)
        setSubTitle(message, R.color.login_text_color)
        setCardBackground(R.color.white)
        setImage(R.drawable.faileddialogicon)
        setPositiveButtonStyle(
            R.color.checkout_success_color,
            activity!!.getString(R.string.try_again_string),
            R.color.black
        )
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        setOnConfirmationBtnClickListener { view: View? -> dismiss() }
        show()
    }
}