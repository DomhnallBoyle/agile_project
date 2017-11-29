using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Automation.Peers;
using System.Windows.Controls;

namespace CSC3045_CS2.Resources.Controls
{
    public class TextBlockPlus : TextBlock
    {
        protected override AutomationPeer OnCreateAutomationPeer()
        {
            return new TextBlockPlusAutomationPeer(this);
        }

        class TextBlockPlusAutomationPeer : TextBlockAutomationPeer
        {
            public TextBlockPlusAutomationPeer(TextBlock owner) : base (owner)
            { }

            protected override bool IsControlElementCore()
            {
                return true;
            }
        }
    }
}
