using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TestStack.White.UIItems;
using TestStack.White.UIItems.Finders;
using TestStack.White.UIItems.WindowItems;

namespace AutomationTests.Util
{
    public class MessageBoxUtil
    {
        public static string GetTextContent(Window messageBox)
        {
            return messageBox.Get<Label>(SearchCriteria.Indexed(0)).Text;
        }

        public static void ClickOKButton(Window messageBox)
        {
            messageBox.Get<Button>(SearchCriteria.Indexed(1)).Click();
        }
    }
}
